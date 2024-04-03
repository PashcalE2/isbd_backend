package main.isbd.services;

import main.isbd.data.order.MessageInterface;
import main.isbd.data.order.OrderInterface;
import main.isbd.data.product.ProductInOrderInfoInterface;
import main.isbd.data.product.ProductInfoInterface;
import main.isbd.data.users.Admin;
import main.isbd.data.users.ClientContactsInterface;
import main.isbd.repositories.AdminRepository;
import main.isbd.utils.CheckRightsInterface;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.annotation.ApplicationScope;

import java.sql.Timestamp;
import java.util.List;

@Service
@Transactional
@ApplicationScope
public class AdminRepositoryService implements CheckRightsInterface {
    private final AdminRepository adminRepository;

    public AdminRepositoryService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    public Boolean checkIfUserIsAuthorized(Integer admin_id, String password) {
        return adminRepository.checkIfAdminIsAuthorized(admin_id, password);
    }

    public Admin getAdminByIdAndPassword(Integer admin_id, String password) {
        return adminRepository.getAdminByIdAndPassword(admin_id, password);
    }

    public ProductInfoInterface getProductInfoById(Integer product_id) {
        return adminRepository.getProductInfoById(product_id);
    }

    public List<OrderInterface> getAllOrdersInfoByAdminId(Integer admin_id) {
        return adminRepository.getAllOrdersInfoByAdminId(admin_id);
    }

    public OrderInterface getOrderInfoByOrderId(Integer order_id) {
        return adminRepository.getOrderInfoByOrderId(order_id);
    }

    public List<ProductInOrderInfoInterface> getAllProductsInOrder(Integer order_id) {
        return adminRepository.getAllProductsInOrder(order_id);
    }

    public void askForOrderAssembling(Integer order_id) {
        adminRepository.askForOrderAssembling(order_id);
    }

    public ClientContactsInterface getClientContactsInChat(Integer order_id) {
        return adminRepository.getClientContactsInChat(order_id);
    }

    public List<MessageInterface> getMessagesInChat(Integer order_id) {
        return adminRepository.getMessagesInChat(order_id);
    }

    public void postMessageInChat(Integer order_id, String content, Timestamp datetime) {
        adminRepository.postMessageInChat(order_id, content, datetime);
    }
}
