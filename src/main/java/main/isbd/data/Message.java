package main.isbd.data;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "Сообщение", schema = "s311817", catalog = "studs")
public class Message {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "ид", nullable = false)
    private Integer id;
    @Basic
    @Column(name = "ид_заказа", nullable = false)
    private Integer order_id;
    @Basic
    @Column(name = "отправитель", nullable = false)
    private Object sender;
    @Basic
    @Column(name = "текст", nullable = false, length = -1)
    private String content;
    @Basic
    @Column(name = "дата_время", nullable = false)
    private Timestamp sent_at;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrderId() {
        return order_id;
    }

    public void setOrderId(Integer order_id) {
        this.order_id = order_id;
    }

    public Object getSender() {
        return sender;
    }

    public void setSender(Object sender) {
        this.sender = sender;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getSentAt() {
        return sent_at;
    }

    public void setSentAt(Timestamp sent_at) {
        this.sent_at = sent_at;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Message message = (Message) o;

        if (!Objects.equals(id, message.id)) return false;
        if (!Objects.equals(order_id, message.order_id)) return false;
        if (!Objects.equals(sender, message.sender)) return false;
        if (!Objects.equals(content, message.content)) return false;
        if (!Objects.equals(sent_at, message.sent_at)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (order_id != null ? order_id.hashCode() : 0);
        result = 31 * result + (sender != null ? sender.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (sent_at != null ? sent_at.hashCode() : 0);
        return result;
    }
}
