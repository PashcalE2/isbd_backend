package main.isbd.services;

import main.isbd.data.material.MaterialInfoInterface;
import main.isbd.data.material.MaterialShortInfoInterface;
import main.isbd.data.product.ProductInfoInterface;
import main.isbd.data.product.ProductShortInfoInterface;
import main.isbd.data.users.Factory;
import main.isbd.repositories.FactoryRepository;
import main.isbd.utils.CheckRightsInterface;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.annotation.ApplicationScope;

import java.util.List;

@Service
@Transactional
@ApplicationScope
public class FactoryRepositoryService implements CheckRightsInterface {
    public final FactoryRepository factoryRepository;

    public FactoryRepositoryService(FactoryRepository factoryRepository) {
        this.factoryRepository = factoryRepository;
    }

    public Boolean checkIfUserIsAuthorized(Integer factory_id, String password) {
        return factoryRepository.checkIfFactoryIsAuthorized(factory_id, password);
    }

    public Factory getFactoryByIdAndPassword(Integer factory_id, String password) {
        return factoryRepository.getFactoryByIdAndPassword(factory_id, password);
    }

    public List<ProductShortInfoInterface> getAllProductsShortInfo() {
        return factoryRepository.getAllProductsShortInfo();
    }

    public ProductInfoInterface getProductInfoById(Integer product_id) {
        return factoryRepository.getProductInfoById(product_id);
    }

    public void setProductInfoById(Integer product_id, String name, String description, Float price) {
        factoryRepository.setProductInfoById(product_id, name, description, price);
    }

    public List<MaterialShortInfoInterface> getAllMaterialsShortInfo() {
        return factoryRepository.getAllMaterialsShortInfo();
    }

    public MaterialInfoInterface getMaterialInfoById(Integer product_id) {
        return factoryRepository.getMaterialInfoById(product_id);
    }

    public void setMaterialInfoById(Integer product_id, String name, String description, Float price) {
        factoryRepository.setMaterialInfoById(product_id, name, description, price);
    }
}
