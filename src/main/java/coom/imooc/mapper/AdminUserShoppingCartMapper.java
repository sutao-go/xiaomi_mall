package coom.imooc.mapper;

import coom.imooc.entity.OrderList;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminUserShoppingCartMapper {
    /**
     * 用户结算购物车中的商品的时候用来显示对应商品的照片的
     * @param userName
     * @return
     */
     List<OrderList> findimg(@Param("userName")String userName);

    /**
     * 用户第一次点击加入购物车时向数据库中加入的数据
     * @param consumer 用户名
     * @param productName 商品名称
     * @param price 商品价格
     * @param quantity 商品数量
     * @return
     */
    int addToShoppingCart(@Param("consumer")String consumer,
                                @Param("productName")String productName,
                                @Param("price")String price,
                                @Param("quantity")Integer quantity
                                );

    /**
     * 查询此用户是否存在
     *
     * @param userName 用户名
     * @return
     */
    Boolean ShoppingRecords(@Param("userName")String userName,@Param("productName")String productName);

    /**
     * 如果用户多次点击添加购物车向数据库中
        添加的商品数量
     * @param quantity2 向数据库中添加的商品数量
     * @param consumer 用户名
     * @return
     */
    int addQuantity(@Param("quantity2")Integer quantity2,@Param("consumer")String consumer,@Param("productName")String productName);

    /**
     * 查询该用户对应的商品数量
     * @param consumer 用户名
     * @return
     */
    String checkProductQuantity(@Param("consumer")String consumer,@Param("productName")String productName);

    List<OrderList> queryProductInformation(@Param("consumer")String consumer);

    /**
     *计算商品的总金额
     * @param userName
     * @return
     */
    List<OrderList> totalAmount(@Param("userName") String userName);

    /**
     * 更新用户购物车中的商品数量
     * @param userName
     * @param productName1
     * @param quantity
     * @return
     */
    int updateQuantity(@Param("quantity")String quantity,
                            @Param("userName")String userName,
                          @Param("productName1")String productName1
                         );
}
