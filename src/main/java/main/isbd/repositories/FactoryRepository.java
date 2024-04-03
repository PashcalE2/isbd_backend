package main.isbd.repositories;

import main.isbd.data.material.MaterialInfoInterface;
import main.isbd.data.material.MaterialShortInfoInterface;
import main.isbd.data.product.ProductInfoInterface;
import main.isbd.data.product.ProductShortInfoInterface;
import main.isbd.data.users.Factory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FactoryRepository extends JpaRepository<Factory, Integer> {
    // PROFILE.CHECK_RIGHTS
    @Query(value = "select * from \"f_аутентифицировать_руководство\"(:factory_id, :password)", nativeQuery = true)
    Boolean checkIfFactoryIsAuthorized(Integer factory_id, String password);

    // PROFILE.LOGIN
    @Query(value = "select * from \"f_g_Завод\"(:factory_id, :password)", nativeQuery = true)
    Factory getFactoryByIdAndPassword(Integer factory_id, String password);

    // PRODUCT.GET_ALL_SHORT
    @Query(value = "select ид as id, название as name, цена as price from \"f_g_короткая_информация_продукции\"()", nativeQuery = true)
    List<ProductShortInfoInterface> getAllProductsShortInfo();

    // PRODUCT.GET
    @Query(value = "select ид as id, название as name, описание as description, цена as price from \"f_g_вся_информация_продукции\"(:product_id)", nativeQuery = true)
    ProductInfoInterface getProductInfoById(Integer product_id);

    // PRODUCT.SET
    @Modifying
    @Query(value = "call \"p_u_информацию_о_продукции\"(:product_id, :name, :description, :price)", nativeQuery = true)
    void setProductInfoById(Integer product_id, String name, String description, Float price);

    // MATERIAL.GET_ALL_SHORT
    @Query(value = "select ид as id, название as name, цена as price from \"f_g_короткая_информация_материалов\"()", nativeQuery = true)
    List<MaterialShortInfoInterface> getAllMaterialsShortInfo();

    // MATERIAL.GET
    @Query(value = "select ид as id, название as name, описание as description, цена as price from \"f_g_вся_информация_материала\"(:product_id)", nativeQuery = true)
    MaterialInfoInterface getMaterialInfoById(Integer product_id);

    // MATERIAL.SET
    @Modifying
    @Query(value = "call \"p_u_информацию_о_материале\"(:product_id, :name, :description, :price)", nativeQuery = true)
    void setMaterialInfoById(Integer product_id, String name, String description, Float price);
}
