# CS3220 — Web and Internet Programming Labs

> Cal State LA · Spring 2026
> Java-based web development with Spring Boot, Spring MVC, FreeMarker, Spring Data JPA, and MySQL.

## Tech Stack
- Java 17+
- Spring Boot 3.x
- Spring MVC
- Spring Data JPA + Hibernate
- MySQL
- Apache FreeMarker (.ftlh templates)
- Bootstrap 5
- Maven

## Labs

### Lab 11 — GuestBook App (Spring Data JPA)
Full-stack web app with user authentication and CRUD operations backed by MySQL.

**Features:**
- User Registration and Login (session-based)
- GuestBook message board (login required)
- Add / Edit / Delete messages
- @OneToMany / @ManyToOne JPA relationships
- Date format: dd MMMM yyyy (e.g. 28 April 2026)
- Bootstrap 5 + external CSS

**Endpoints:**
- GET / — Login page
- POST /login — Process login
- GET /logout — Logout
- GET /register — Register page
- POST /register — Process registration
- GET /guestbook — View all messages
- POST /guestbook/add — Add message
- POST /guestbook/edit/{id} — Edit message
- POST /guestbook/delete/{id} — Delete message

## Setup

### MySQL
Start MySQL:
sudo /usr/local/mysql/support-files/mysql.server start

Update application.properties:
spring.datasource.password=root1234

### Run in Eclipse
Right-click JpaApplication.java → Run As → Java Application
Then open: http://localhost:8080

## Author
Erfan Mirzaee — CS3220 Web and Internet Programming — Cal State LA Spring 2026
