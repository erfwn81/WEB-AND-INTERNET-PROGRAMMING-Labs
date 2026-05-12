package cs3220.controller;

import cs3220.dto.MessageEntryDto;
import cs3220.dto.UserEntryDto;
import cs3220.model.MessageEntry;
import cs3220.model.UserEntry;
import cs3220.repository.MessageEntryRepository;
import cs3220.repository.UserEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ApiController {

    @Autowired private UserEntryRepository userRepo;
    @Autowired private MessageEntryRepository messageRepo;

    // -------- User: Register --------
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserEntryDto register(@RequestBody UserEntryDto dto) {
        if (isBlank(dto.getEmail()) || isBlank(dto.getName()) || isBlank(dto.getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "All fields are required");
        }
        if (userRepo.existsByEmail(dto.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already registered");
        }
        UserEntry saved = userRepo.save(new UserEntry(dto.getEmail(), dto.getName(), dto.getPassword()));
        return UserEntryDto.fromEntity(saved);
    }

    // -------- User: Login --------
    @PostMapping("/login")
    public UserEntryDto login(@RequestBody UserEntryDto dto) {
        Optional<UserEntry> u = userRepo.findByEmail(dto.getEmail());
        if (u.isEmpty() || !u.get().getPassword().equals(dto.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Email and Password does not match");
        }
        return UserEntryDto.fromEntity(u.get());
    }

    // -------- Messages: List --------
    @GetMapping("/messages")
    public List<MessageEntryDto> listMessages() {
        return messageRepo.findAllByOrderByCreatedAtDesc()
                .stream().map(MessageEntryDto::fromEntity).toList();
    }

    // -------- Messages: Add --------
    @PostMapping("/messages")
    @ResponseStatus(HttpStatus.CREATED)
    public MessageEntryDto addMessage(@RequestBody Map<String, Object> body) {
        Long userId = parseLong(body.get("userId"));
        String content = (String) body.get("content");
        if (userId == null || isBlank(content)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "userId and content are required");
        }
        UserEntry user = userRepo.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid user"));
        MessageEntry saved = messageRepo.save(new MessageEntry(content, user));
        return MessageEntryDto.fromEntity(saved);
    }

    // -------- Messages: Edit (owner only) --------
    @PutMapping("/messages/{id}")
    public MessageEntryDto editMessage(@PathVariable Long id, @RequestBody Map<String, Object> body) {
        Long userId = parseLong(body.get("userId"));
        String content = (String) body.get("content");
        MessageEntry m = messageRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Message not found"));
        if (userId == null || !m.getUser().getId().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not the owner of this message");
        }
        if (isBlank(content)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "content is required");
        }
        m.setContent(content);
        return MessageEntryDto.fromEntity(messageRepo.save(m));
    }

    // -------- Messages: Delete (owner only) --------
    @DeleteMapping("/messages/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMessage(@PathVariable Long id, @RequestParam Long userId) {
        MessageEntry m = messageRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Message not found"));
        if (!m.getUser().getId().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not the owner of this message");
        }
        messageRepo.delete(m);
    }

    // -------- helpers --------
    private static boolean isBlank(String s) { return s == null || s.trim().isEmpty(); }
    private static Long parseLong(Object o) {
        if (o == null) return null;
        if (o instanceof Number n) return n.longValue();
        try { return Long.parseLong(o.toString()); } catch (Exception e) { return null; }
    }
}