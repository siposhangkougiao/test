package com.mtnz.controller.app.mysql.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Table(name = "order_kuncun")
public class OrderKuCun {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "order_kucun_id")
  private Long orderKucunId;

  @Column(name = "order_pro_id")
  private Long orderProId;

  @Column(name = "product_id")
  private Long productId;

  @Column(name = "kuncun_id")
  private Long kuncunId;

  @Column(name = "product_price")
  private BigDecimal productPrice;

  @Column(name = "purchase_price")
  private BigDecimal purchasePrice;

  @Column(name = "num")
  private Integer num;

  @Column(name = "nums")
  private Integer nums;

  @Column(name = "all_number")
  private BigDecimal allNumber;

  @Column(name = "now_number")
  private BigDecimal nowNumber;

  @Column(name = "order_info_id")
  private Long orderInfoId;

  @Column(name = "store_id")
  private Long storeId;

  @Column(name = "date")
  private Date date;

  @Column(name = "revokes")
  private String revokes;

  @Column(name = "status")
  private String status;


}
