package HCMUTE.SocialMedia.Models;

public class ConversationCardModel {
    private long conversationId;
    private String conversationAvatar;
    private String conversationName;

    public ConversationCardModel(long conversationId, String conversationAvatar, String conversationName) {
        this.conversationId = conversationId;
        this.conversationAvatar = conversationAvatar;
        this.conversationName = conversationName;
    }

    public long getConversationId() {
        return conversationId;
    }

    public void setConversationId(long conversationId) {
        this.conversationId = conversationId;
    }

    public String getConversationAvatar() {
        return conversationAvatar;
    }

    public void setConversationAvatar(String conversationAvatar) {
        this.conversationAvatar = conversationAvatar;
    }

    public String getConversationName() {
        return conversationName;
    }

    public void setConversationName(String conversationName) {
        this.conversationName = conversationName;
    }
}
