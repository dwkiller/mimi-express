package com.mimi.core.express.service.shop.impl;

import com.mimi.core.common.superpackage.service.impl.TenantServiceImpl;
import com.mimi.core.express.entity.shop.ShopCoupon;
import com.mimi.core.express.mapper.shop.ShopCouponMapper;
import com.mimi.core.express.service.shop.ShopCouponService;
import org.springframework.stereotype.Service;

@Service
public class ShopCouponServiceImpl extends TenantServiceImpl<ShopCouponMapper, ShopCoupon> implements ShopCouponService {

}
