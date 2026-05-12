/*
 * Single JS file used by both index.ftlh and messageboard.ftlh.
 * It detects which page it is on by the presence of marker elements
 * and wires up the relevant AJAX handlers. userId is persisted in
 * localStorage because the REST API is stateless.
 */
$(function () {

    const STORAGE_KEY = 'cs3220.user';

    const App = {

        // ---------- session helpers ----------
        getUser: function () {
            const raw = localStorage.getItem(STORAGE_KEY);
            return raw ? JSON.parse(raw) : null;
        },
        setUser: function (user) { localStorage.setItem(STORAGE_KEY, JSON.stringify(user)); },
        clearUser: function () { localStorage.removeItem(STORAGE_KEY); },

        // ---------- date formatter ----------
        formatDate: function (iso) {
            // The DTO already returns "MM/dd/yyyy hh:mm a", but if a raw
            // ISO string ever slips through we fall back here.
            if (!iso) return '';
            if (/^\d{2}\/\d{2}\/\d{4}/.test(iso)) return iso;
            const d = new Date(iso);
            return d.toLocaleString('en-US', {
                year: 'numeric', month: '2-digit', day: '2-digit',
                hour: '2-digit', minute: '2-digit'
            });
        },

        // ---------- login / register page ----------
        initAuthPage: function () {
            // already logged in? skip straight to the board
            if (this.getUser()) { window.location.href = '/messageboard'; return; }

            $('#showRegisterBtn').on('click', () => {
                $('#loginCard').addClass('d-none');
                $('#registerCard').removeClass('d-none');
            });
            $('#showLoginBtn').on('click', () => {
                $('#registerCard').addClass('d-none');
                $('#loginCard').removeClass('d-none');
            });

            $('#loginBtn').on('click', this.doLogin.bind(this));
            $('#registerBtn').on('click', this.doRegister.bind(this));
        },

        doLogin: function () {
            const payload = {
                email: $('#loginEmail').val().trim(),
                password: $('#loginPassword').val()
            };
            $('#loginError').text('');
            if (!payload.email || !payload.password) {
                $('#loginError').text('Email and Password are required');
                return;
            }
            $.ajax({
                url: '/api/login',
                type: 'POST',
                contentType: 'application/json',
                data: JSON.stringify(payload),
                success: (user) => {
                    this.setUser(user);
                    window.location.href = '/messageboard';
                },
                error: () => { $('#loginError').text('Email and Password does not match'); }
            });
        },

        doRegister: function () {
            const payload = {
                email: $('#regEmail').val().trim(),
                name: $('#regName').val().trim(),
                password: $('#regPassword').val()
            };
            $('#registerError').text('');
            if (!payload.email || !payload.name || !payload.password) {
                $('#registerError').text('All fields are required');
                return;
            }
            $.ajax({
                url: '/api/register',
                type: 'POST',
                contentType: 'application/json',
                data: JSON.stringify(payload),
                success: (user) => {
                    this.setUser(user);
                    window.location.href = '/messageboard';
                },
                error: (xhr) => {
                    if (xhr.status === 409) {
                        $('#registerError').text('Email already registered');
                    } else {
                        $('#registerError').text('Registration failed');
                    }
                }
            });
        },

        // ---------- message board page ----------
        initBoardPage: function () {
            const user = this.getUser();
            if (!user) { window.location.href = '/'; return; } // access control

            $('#currentUserName').text(user.name);
            $('#logoutBtn').on('click', () => {
                this.clearUser();
                window.location.href = '/';
            });
            $('#saveBtn').on('click', this.saveMessage.bind(this));
            $('#cancelEditBtn').on('click', this.cancelEdit.bind(this));

            this.loadMessages();
        },

        loadMessages: function () {
            $.ajax({
                url: '/api/messages',
                type: 'GET',
                success: (messages) => this.renderMessages(messages),
                error: () => $('#messagesContainer').html(
                    '<div class="alert alert-danger">Failed to load messages.</div>')
            });
        },

        renderMessages: function (messages) {
            const me = this.getUser();
            const container = $('#messagesContainer').empty();
            if (!messages.length) {
                container.html('<div class="text-muted">No messages yet. Be the first to post!</div>');
                return;
            }
            messages.forEach((m) => {
                const isOwner = m.userId === me.id;
                const actions = isOwner ? `
                    <div class="message-actions">
                        <button class="btn btn-sm btn-outline-primary edit-btn"
                                data-id="${m.id}" data-content="${$('<div>').text(m.content).html()}">Edit</button>
                        <button class="btn btn-sm btn-outline-danger delete-btn"
                                data-id="${m.id}">Delete</button>
                    </div>` : '';
                container.append(`
                    <div class="card message-card">
                        <div class="card-body">
                            <div class="d-flex justify-content-between align-items-start">
                                <div>
                                    <strong>${$('<div>').text(m.userName).html()}</strong>
                                    <div class="message-meta">${this.formatDate(m.createdAt)}</div>
                                </div>
                                ${actions}
                            </div>
                            <p class="mt-2 mb-0">${$('<div>').text(m.content).html()}</p>
                        </div>
                    </div>`);
            });
            $('.edit-btn').on('click', (e) => this.startEdit(
                $(e.currentTarget).data('id'),
                $(e.currentTarget).data('content')));
            $('.delete-btn').on('click', (e) => this.deleteMessage(
                $(e.currentTarget).data('id')));
        },

        saveMessage: function () {
            const me = this.getUser();
            const content = $('#messageContent').val().trim();
            if (!content) return;
            const editingId = $('#editingId').val();

            if (editingId) {
                $.ajax({
                    url: '/api/messages/' + editingId,
                    type: 'PUT',
                    contentType: 'application/json',
                    data: JSON.stringify({ userId: me.id, content: content }),
                    success: () => { this.cancelEdit(); this.loadMessages(); },
                    error: () => alert('Could not edit message')
                });
            } else {
                $.ajax({
                    url: '/api/messages',
                    type: 'POST',
                    contentType: 'application/json',
                    data: JSON.stringify({ userId: me.id, content: content }),
                    success: () => { $('#messageContent').val(''); this.loadMessages(); },
                    error: () => alert('Could not add message')
                });
            }
        },

        startEdit: function (id, content) {
            $('#editingId').val(id);
            $('#messageContent').val(content);
            $('#formTitle').text('Edit Message');
            $('#saveBtn').text('Save Changes');
            $('#cancelEditBtn').removeClass('d-none');
            window.scrollTo({ top: 0, behavior: 'smooth' });
        },

        cancelEdit: function () {
            $('#editingId').val('');
            $('#messageContent').val('');
            $('#formTitle').text('New Message');
            $('#saveBtn').text('Post');
            $('#cancelEditBtn').addClass('d-none');
        },

        deleteMessage: function (id) {
            if (!confirm('Delete this message?')) return;
            const me = this.getUser();
            $.ajax({
                url: '/api/messages/' + id + '?userId=' + me.id,
                type: 'DELETE',
                success: () => this.loadMessages(),
                error: () => alert('Could not delete message')
            });
        }
    };

    // Wire to whichever page is loaded
    if ($('#loginCard').length)         App.initAuthPage();
    else if ($('#messagesContainer').length) App.initBoardPage();
});