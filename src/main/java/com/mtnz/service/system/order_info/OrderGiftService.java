package com.mtnz.service.system.order_info;

import com.mtnz.controller.app.order.model.OrderGift;
import com.mtnz.dao.DaoSupport;
import com.mtnz.util.PageData;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


@Service("orderGiftService")
public class OrderGiftService {

    @Resource(name = "daoSupport")
    private DaoSupport daoSupport;


    /**
     * 添加订单的时候添加赠品
     * @param list
     * @throws Exception
     */
    public void saveGift(List<OrderGift> list,Long order_info_id) throws Exception {
        for (int i = 0; i < list.size(); i++) {
            OrderGift orderGift = list.get(i);
            orderGift.setOrder_info_id(order_info_id);
            daoSupport.save("OrderGiftMapper.saveGift",orderGift);
        }
    }

    /**
     * 撤销或者退货的时候修改状态
     */
    public void updateIsPass(Long order_info_id) throws Exception {
        Map map = new HashedMap();
        map.put("order_info_id",order_info_id);
        map.put("is_back",1);
        daoSupport.update("OrderGiftMapper.updateIsPass",map);
    }

    public List<PageData> findGiftByOrderInfoId(PageData pd) throws Exception {
        return (List<PageData>) daoSupport.findForList("OrderGiftMapper.findGiftByOrderInfoId",pd);
    }

    public void editGiftBack(PageData pd) throws Exception {
        daoSupport.update("OrderGiftMapper.editGiftBack",pd);
    }
}
