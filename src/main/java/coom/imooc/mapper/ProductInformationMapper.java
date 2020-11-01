package coom.imooc.mapper;

import coom.imooc.entity.SalesManagement;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ProductInformationMapper {

    List<SalesManagement> findProduct(String allProduct);

    int changeSalesStatus(@Param("id") String id,@Param("status") String status);

    Object findStatus(String productName);
}
