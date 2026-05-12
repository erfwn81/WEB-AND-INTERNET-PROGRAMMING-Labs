package cs3220.dto;

import cs3220.model.MessageEntry;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;

public class MessageEntryDto {
    private Long id;
    private String content;

    @JsonFormat(pattern = "MM/dd/yyyy hh:mm a")
    private LocalDateTime createdAt;

    private Long userId;
    private String userName;

    public MessageEntryDto() {}

    public static MessageEntryDto fromEntity(MessageEntry m) {
        MessageEntryDto dto = new MessageEntryDto();
        dto.id = m.getId();
        dto.content = m.getContent();
        dto.createdAt = m.getCreatedAt();
        dto.userId = m.getUser().getId();
        dto.userName = m.getUser().getName();
        return dto;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }
}