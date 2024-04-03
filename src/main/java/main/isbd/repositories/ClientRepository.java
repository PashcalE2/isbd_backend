package main.isbd.repositories;

import main.isbd.data.order.MessageInterface;
import main.isbd.data.order.OrderInterface;
import main.isbd.data.product.ProductInOrderInfoInterface;
import main.isbd.data.product.ProductInfoInterface;
import main.isbd.data.product.ProductShortInfoInterface;
import main.isbd.data.users.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public interface ClientRepository extends JpaRepository<Client, Integer> {
    // GET_REGISTERED_ORGANIZATIONS
    @Query(value = "select * from \"f_g_организации_с_аккаунтом\"()", nativeQuery = true)
    List<String> getAllRegisteredOrganizations();

    // GET_NOT_REGISTERED_ORGANIZATIONS
    @Query(value = "select * from \"f_g_организации_без_аккаунта\"()", nativeQuery = true)
    List<String> getAllNotRegisteredOrganizations();

    // CHECK_RIGHTS
    @Query(value = "select * from \"f_аутентифицировать\"(:id, :password)", nativeQuery = true)
    Boolean checkIfClientIsAuthorised(Integer id, String password);

    // LOGIN
    @Query(value = "select * from \"f_g_Клиент\"(:name, :password)", nativeQuery = true)
    Client getClientByNameAndPassword(String name, String password);

    // REGISTER
    @Query(value = "select * from \"f_i_Клиент\"(:phone_number, :email, :password, :name)", nativeQuery = true)
    Client registerClient(String phone_number, String email, String password, String name);

    // GET_PROFILE
    @Query(value = "select номер_телефона as phoneNumber, email as email from \"f_g_контакты_клиента\"(:client_id)", nativeQuery = true)
    ClientProfileInterface getClientProfileById(Integer client_id);

    // SET_PROFILE
    @Modifying
    @Query(value = "call \"p_u_контакты_клиента\"(:client_id, :phone_number, :email)", nativeQuery = true)
    void setClientProfileById(Integer client_id, String phone_number, String email);

    // PRODUCT.GET_ALL_SHORT
    @Query(value = "select ид as id, название as name, цена as price from \"f_g_короткая_информация_продукции\"()", nativeQuery = true)
    List<ProductShortInfoInterface> getAllProductsShortInfo();

    // PRODUCT.GET
    @Query(value = "select ид as id, название as name, описание as description, цена as price from \"f_g_вся_информация_продукции\"(:product_id)", nativeQuery = true)
    ProductInfoInterface getProductInfoById(Integer product_id);

    // PRODUCT.ADD_TO_ORDER
    @Modifying
    @Query(value = "call \"p_i_добавить_продукцию_к_заказу\"(:order_id, :product_id)", nativeQuery = true)
    void addProductToOrder(Integer order_id, Integer product_id);

    // PRODUCT.REMOVE_FROM_ORDER
    @Modifying
    @Query(value = "call \"p_d_убрать_продукцию_из_заказа\"(:order_id, :product_id)", nativeQuery = true)
    void removeProductFromOrder(Integer order_id, Integer product_id);

    // ORDER.GET_ALL_INFO
    @Query(value = "select ид as id, ид_клиента as clientId, ид_консультанта as adminId, статус as status, поступил as formedAt, завершен as doneAt, итоговая_сумма as sum from \"f_g_все_заказы_клиента\"(:client_id)", nativeQuery = true)
    List<OrderInterface> getAllOrdersInfoByClientId(Integer client_id);

    // ORDER.GET
    @Query(value = "select ид as id, ид_клиента as clientId, ид_консультанта as adminId, статус as status, поступил as formedAt, завершен as doneAt, итоговая_сумма as sum from \"f_g_Заказ\"(:order_id)", nativeQuery = true)
    OrderInterface getOrderInfoByOrderId(Integer order_id);

    // ORDER.GET_CURRENT
    @Query(value = "select ид as id, ид_клиента as clientId, ид_консультанта as adminId, статус as status, поступил as formedAt, завершен as doneAt, итоговая_сумма as sum from \"f_g_текущий_заказ_клиента\"(:client_id)", nativeQuery = true)
    OrderInterface getCurrentOrderInfoByClientId(Integer client_id);

    // ORDER.GET_PRODUCTS
    @Query(value = "select ид_типа as id, статус as status, количество as count from \"f_g_вся_продукция_в_заказе\"(:order_id)", nativeQuery = true)
    List<ProductInOrderInfoInterface> getAllProductsInOrder(Integer order_id);

    // ORDER.SET_PRODUCT_COUNT
    @Modifying
    @Query(value = "call \"p_u_изменить_количество_продукции\"(:order_id, :product_id, :count)", nativeQuery = true)
    void setProductCountInOrder(Integer order_id, Integer product_id, Integer count);

    // ORDER.ACCEPT
    @Modifying
    @Query(value = "call \"p_u_подтвердить_заказ\"(:order_id)", nativeQuery = true)
    void acceptOrder(Integer order_id);

    // ORDER.PAY
    @Modifying
    @Query(value = "call \"p_u_оплата_заказа\"(:order_id)", nativeQuery = true)
    void payForOrder(Integer order_id);

    // ORDER.CANCEL
    @Modifying
    @Query(value = "call \"p_u_заказ_отклонен\"(:order_id, :done_at)", nativeQuery = true)
    void cancelOrder(Integer order_id, Timestamp done_at);

    // CHAT.GET_ADMIN
    @Query(value = "select \"ФИО\" as fullName, номер_телефона as phoneNumber, email as email from \"f_g_контакты_консультанта_в_чате\"(:order_id)", nativeQuery = true)
    AdminContactsInterface getAdminContactsInChat(Integer order_id);

    // CHAT.GET_MESSAGES
    @Query(value = "select ид as id, отправитель as sender, текст as content, дата_время as sentAt from \"f_g_все_сообщения\"(:order_id)", nativeQuery = true)
    List<MessageInterface> getMessagesInChat(Integer order_id);

    // CHAT.POST_MESSAGE
    @Modifying
    @Query(value = "call \"p_i_сообщение_клиента\"(:order_id, :content, :datetime)", nativeQuery = true)
    void postMessageInChat(Integer order_id, String content, Timestamp datetime);
}
