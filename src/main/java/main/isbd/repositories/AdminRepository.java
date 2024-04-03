package main.isbd.repositories;

import main.isbd.data.order.MessageInterface;
import main.isbd.data.order.OrderInterface;
import main.isbd.data.product.ProductInOrderInfoInterface;
import main.isbd.data.product.ProductInfoInterface;
import main.isbd.data.users.Admin;
import main.isbd.data.users.ClientContactsInterface;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.sql.Timestamp;
import java.util.List;

public interface AdminRepository extends JpaRepository<Admin, Integer> {
    // PROFILE.CHECK_RIGHTS
    @Query(value = "select * from \"f_аутентифицировать_консультанта\"(:admin_id, :password)", nativeQuery = true)
    Boolean checkIfAdminIsAuthorized(Integer admin_id, String password);

    // PROFILE.LOGIN
    @Query(value = "select * from \"f_g_Консультант\"(:admin_id, :password)", nativeQuery = true)
    Admin getAdminByIdAndPassword(Integer admin_id, String password);

    // PRODUCT.GET
    @Query(value = "select ид as id, название as name, описание as description, цена as price from \"f_g_вся_информация_продукции\"(:product_id)", nativeQuery = true)
    ProductInfoInterface getProductInfoById(Integer product_id);

    // ORDER.GET_ALL_INFO
    @Query(value = "select ид as id, ид_клиента as clientId, ид_консультанта as adminId, статус as status, поступил as formedAt, завершен as doneAt, итоговая_сумма as sum from \"f_g_все_заказы_консультанта\"(:admin_id)", nativeQuery = true)
    List<OrderInterface> getAllOrdersInfoByAdminId(Integer admin_id);

    // ORDER.GET
    @Query(value = "select ид as id, ид_клиента as clientId, ид_консультанта as adminId, статус as status, поступил as formedAt, завершен as doneAt, итоговая_сумма as sum from \"f_g_Заказ\"(:order_id)", nativeQuery = true)
    OrderInterface getOrderInfoByOrderId(Integer order_id);

    // ORDER.GET_PRODUCTS
    @Query(value = "select ид_типа as id, статус as status, количество as count from \"f_g_вся_продукция_в_заказе\"(:order_id)", nativeQuery = true)
    List<ProductInOrderInfoInterface> getAllProductsInOrder(Integer order_id);

    // ORDER.ASK_FOR_UPDATE
    @Modifying
    @Query(value = "call \"p_u_собрать_заказ\"(:order_id)", nativeQuery = true)
    void askForOrderAssembling(Integer order_id);

    // CHAT.GET_CLIENT
    @Query(value = "select название as organization, номер_телефона as phoneNumber, email as email from \"f_g_контакты_клиента_в_чате\"(:order_id)", nativeQuery = true)
    ClientContactsInterface getClientContactsInChat(Integer order_id);

    // CHAT.GET_MESSAGES
    @Query(value = "select ид as id, отправитель as sender, текст as content, дата_время as sentAt from \"f_g_все_сообщения\"(:order_id)", nativeQuery = true)
    List<MessageInterface> getMessagesInChat(Integer order_id);

    // CHAT.POST_MESSAGE
    @Modifying
    @Query(value = "call \"p_i_сообщение_консультанта\"(:order_id, :content, :datetime)", nativeQuery = true)
    void postMessageInChat(Integer order_id, String content, Timestamp datetime);
}
