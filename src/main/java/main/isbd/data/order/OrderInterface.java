package main.isbd.data.order;

import java.sql.Timestamp;

public interface OrderInterface {
    Integer getId();
    Integer getClientId();
    Integer getAdminId();
    String getStatus();
    Timestamp getFormedAt();
    Timestamp getDoneAt();
    Float getSum();
}
