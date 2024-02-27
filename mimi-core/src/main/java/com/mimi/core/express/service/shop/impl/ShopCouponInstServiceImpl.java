package com.mimi.core.express.service.shop.impl;

import com.mimi.core.common.superpackage.service.impl.TenantServiceImpl;
import com.mimi.core.express.entity.shop.ShopCouponInst;
import com.mimi.core.express.mapper.shop.ShopCouponInstMapper;
import com.mimi.core.express.service.shop.ShopCouponInstService;
import org.springframework.stereotype.Service;

@Service
public class ShopCouponInstServiceImpl extends TenantServiceImpl<ShopCouponInstMapper, ShopCouponInst>
        implements ShopCouponInstService {

}
