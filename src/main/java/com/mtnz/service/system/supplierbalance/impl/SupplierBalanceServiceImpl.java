package com.mtnz.service.system.supplierbalance.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mtnz.controller.app.supplierbalance.model.ReturnBean;
import com.mtnz.controller.app.supplierbalance.model.SupplierBalanceDetail;
import com.mtnz.controller.app.supplierbalance.model.SupplierBalanceOwe;
import com.mtnz.controller.base.ServiceException;
import com.mtnz.service.system.supplier.SupplierService;
import com.mtnz.service.system.supplierbalance.SupplierBalanceService;
import com.mtnz.sql.system.supplierbalance.SupplierBalanceDetailMapper;
import com.mtnz.sql.system.supplierbalance.SupplierBalanceOweMapper;
import com.mtnz.util.PageData;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


@Service
public class SupplierBalanceServiceImpl implements SupplierBalanceService {

    @Resource
    private SupplierBalanceOweMapper supplierBalanceOweMapper;

    @Resource
    private SupplierBalanceDetailMapper supplierBalanceDetailMapper;

    @Resource
    private SupplierService supplierService;

    /**
     * 查询预付款明细列表
     * @param supplierBalanceOwe
     * @return
     */
    @Override
    public ReturnBean select(SupplierBalanceOwe supplierBalanceOwe) throws Exception {
        ReturnBean returnBean = new ReturnBean();
        SupplierBalanceOwe seBean = new SupplierBalanceOwe();
        seBean.setStoreId(supplierBalanceOwe.getStoreId());
        seBean.setSupplierId(supplierBalanceOwe.getSupplierId());
        SupplierBalanceOwe selectBean = supplierBalanceOweMapper.selectOne(seBean);
        if(selectBean==null){
            PageData pageData = new PageData();
            pageData.put("supplier_id",supplierBalanceOwe.getSupplierId());
            PageData byId = supplierService.findById(pageData);
            if(byId==null){
                throw new ServiceException(-101,"供货商未找到",null);
            }
            SupplierBalanceOwe inBean = new SupplierBalanceOwe();
            inBean.setSupplierId(supplierBalanceOwe.getSupplierId());
            inBean.setUserId(supplierBalanceOwe.getUserId());
            inBean.setStoreId(supplierBalanceOwe.getStoreId());
            inBean.setName(byId.getString("gname"));
            supplierBalanceOweMapper.insertSelective(inBean);
            Long id = inBean.getId();
            SupplierBalanceOwe bean = supplierBalanceOweMapper.selectByPrimaryKey(id);
            returnBean.setSupplierBalanceOwe(bean);
        }else {
            returnBean.setSupplierBalanceOwe(selectBean);
        }
        Example example = new Example(SupplierBalanceDetail.class);
        example.and().andEqualTo("storeId",supplierBalanceOwe.getStoreId());
        example.and().andEqualTo("supplierId",supplierBalanceOwe.getSupplierId());
        example.orderBy("id").desc();
        PageHelper.startPage(supplierBalanceOwe.getPageNumber(),supplierBalanceOwe.getPageSize());
        List<SupplierBalanceDetail> list = supplierBalanceDetailMapper.selectByExample(example);
        PageInfo pageInfo = new PageInfo(list);
        returnBean.setPageInfo(pageInfo);
        return returnBean;
    }

    /**
     * 添加明细
     * @param supplierBalanceDetail
     * @return
     */
    @Override
    public int insert(SupplierBalanceDetail supplierBalanceDetail) throws Exception {
        SupplierBalanceOwe seBean = new SupplierBalanceOwe();
        seBean.setStoreId(supplierBalanceDetail.getStoreId());
        seBean.setSupplierId(supplierBalanceDetail.getSupplierId());
        SupplierBalanceOwe selectBean = supplierBalanceOweMapper.selectOne(seBean);
        if(selectBean==null){
            PageData pageData = new PageData();
            pageData.put("supplier_id",supplierBalanceDetail.getSupplierId());
            PageData byId = supplierService.findById(pageData);
            if(byId==null){
                throw new ServiceException(-101,"供货商未找到",null);
            }
            SupplierBalanceOwe inBean = new SupplierBalanceOwe();
            inBean.setSupplierId(supplierBalanceDetail.getSupplierId());
            inBean.setUserId(supplierBalanceDetail.getUserId());
            inBean.setStoreId(supplierBalanceDetail.getStoreId());
            inBean.setName(byId.getString("gname"));
            supplierBalanceOweMapper.insertSelective(inBean);
        }
        if(supplierBalanceDetail.getType()==2){//还款
            SupplierBalanceOwe supplierBalanceOwe = new SupplierBalanceOwe();
            supplierBalanceOwe.setStoreId(supplierBalanceDetail.getStoreId());
            supplierBalanceOwe.setSupplierId(supplierBalanceDetail.getSupplierId());
            SupplierBalanceOwe bean = supplierBalanceOweMapper.selectOne(supplierBalanceOwe);
            BigDecimal owe = bean.getOwePrice();//欠款
            BigDecimal money = supplierBalanceDetail.getMoney();
            SupplierBalanceOwe upbean = new SupplierBalanceOwe();
            upbean.setId(bean.getId());
            if(owe.compareTo(new BigDecimal(0))==1){//有欠款
                if(owe.compareTo(money)==1){
                    upbean.setOwePrice(owe.subtract(money));
                }else {
                    throw new ServiceException(-105,"不可以多还钱",null);
                    //upbean.setOwePrice(new BigDecimal(0));
                }
            }else {
                upbean.setOwePrice(new BigDecimal(0));
            }
            supplierBalanceOweMapper.updateByPrimaryKeySelective(upbean);
        }
        if(supplierBalanceDetail.getType()==1){//充值
            SupplierBalanceOwe supplierBalanceOwe = new SupplierBalanceOwe();
            supplierBalanceOwe.setStoreId(supplierBalanceDetail.getStoreId());
            supplierBalanceOwe.setSupplierId(supplierBalanceDetail.getSupplierId());
            SupplierBalanceOwe bean = supplierBalanceOweMapper.selectOne(supplierBalanceOwe);
            BigDecimal prePrice = bean.getPrePrice();//余额
            BigDecimal money = supplierBalanceDetail.getMoney();
            SupplierBalanceOwe upbean = new SupplierBalanceOwe();
            upbean.setId(bean.getId());
            upbean.setPrePrice(prePrice.add(money));
            supplierBalanceOweMapper.updateByPrimaryKeySelective(upbean);
        }
        return supplierBalanceDetailMapper.insertSelective(supplierBalanceDetail);
    }

    /**
     * 查询预付款明细
     * @param id
     * @return
     */
    @Override
    public SupplierBalanceOwe selectOne(Long id) throws Exception {
        SupplierBalanceDetail supplierBalanceDetail = supplierBalanceDetailMapper.selectByPrimaryKey(id);
        SupplierBalanceOwe bean = new SupplierBalanceOwe();
        bean.setSupplierId(supplierBalanceDetail.getSupplierId());
        bean.setStoreId(supplierBalanceDetail.getStoreId());
        SupplierBalanceOwe rebean = supplierBalanceOweMapper.selectOne(bean);
        PageData pd = new PageData();
        pd.put("supplier_id",rebean.getSupplierId());
        PageData pageData = supplierService.findById(pd);
        if(pageData.get("name")!=null){
            supplierBalanceDetail.setOpenName(pageData.get("gname").toString());
        }
        if(supplierBalanceDetail.getBackId()!=null){
            PageData pd1= new PageData();
            pd1.put("supplier_id",supplierBalanceDetail.getBackId());
            PageData pageData1 = supplierService.findById(pd);
            if(pageData1!=null){
                supplierBalanceDetail.setBackName(pageData.get("gname").toString());
            }
        }
        rebean.setSupplierBalanceDetail(supplierBalanceDetail);
        return rebean;
    }

    /**
     * 撤单
     * @param id
     * @return
     */
    @Override
    public int reback(Long id,Long uid) {
        SupplierBalanceDetail supplierBalanceDetail = supplierBalanceDetailMapper.selectByPrimaryKey(id);
        SupplierBalanceDetail updetail = new SupplierBalanceDetail();
        updetail.setBackId(uid);
        updetail.setId(id);
        updetail.setBackTime(new Date());
        updetail.setIsBack(1);
        supplierBalanceDetailMapper.updateByPrimaryKeySelective(updetail);
        BigDecimal price = supplierBalanceDetail.getMoney();
        if(supplierBalanceDetail.getType()==1){//减余额
            SupplierBalanceOwe bean = new SupplierBalanceOwe();
            bean.setSupplierId(supplierBalanceDetail.getSupplierId());
            bean.setStoreId(supplierBalanceDetail.getStoreId());
            SupplierBalanceOwe rebean = supplierBalanceOweMapper.selectOne(bean);
            if(rebean.getPrePrice().compareTo(price)<0){
                throw new ServiceException(-103,"预付款不足,不可撤单！",null);
            }
            SupplierBalanceOwe upbean = new SupplierBalanceOwe();
            upbean.setId(rebean.getId());
            upbean.setPrePrice(rebean.getPrePrice().subtract(supplierBalanceDetail.getMoney()));
            supplierBalanceOweMapper.updateByPrimaryKeySelective(upbean);
        }
        if(supplierBalanceDetail.getType()==2){//加欠款
            SupplierBalanceOwe bean = new SupplierBalanceOwe();
            bean.setSupplierId(supplierBalanceDetail.getSupplierId());
            bean.setStoreId(supplierBalanceDetail.getStoreId());
            SupplierBalanceOwe rebean = supplierBalanceOweMapper.selectOne(bean);
            SupplierBalanceOwe upbean = new SupplierBalanceOwe();
            upbean.setId(rebean.getId());
            upbean.setPrePrice(rebean.getOwePrice().add(supplierBalanceDetail.getMoney()));
            supplierBalanceOweMapper.updateByPrimaryKeySelective(upbean);
        }
        return 1;
    }

    /**
     * 初始化金额
     * @param supplierBalanceOwe
     * @return
     */
    @Override
    public int updatebegin(SupplierBalanceOwe supplierBalanceOwe) {
        SupplierBalanceOwe bean = supplierBalanceOweMapper.selectByPrimaryKey(supplierBalanceOwe.getId());
        if(supplierBalanceOwe.getType()==1){//初始化欠款
            supplierBalanceOwe.setOwePrice(supplierBalanceOwe.getOwePrice());
            SupplierBalanceDetail sbean = new SupplierBalanceDetail();
            sbean.setMoney(supplierBalanceOwe.getOwePrice());
            sbean.setRemark(supplierBalanceOwe.getRemark());
            sbean.setStoreId(bean.getStoreId());
            sbean.setSupplierId(bean.getSupplierId());
            sbean.setUserId(bean.getUserId());
            sbean.setType(3);
            sbean.setCreatTime(supplierBalanceOwe.getCreatTime());
            supplierBalanceDetailMapper.insertSelective(sbean);
        }
        if(supplierBalanceOwe.getType()==2){//初始化余额
            supplierBalanceOwe.setPrePrice(supplierBalanceOwe.getPrePrice());
            SupplierBalanceDetail sbean = new SupplierBalanceDetail();
            sbean.setMoney(supplierBalanceOwe.getPrePrice());
            sbean.setRemark(supplierBalanceOwe.getRemark());
            sbean.setStoreId(bean.getStoreId());
            sbean.setSupplierId(bean.getSupplierId());
            sbean.setUserId(bean.getUserId());
            sbean.setType(4);
            sbean.setCreatTime(supplierBalanceOwe.getCreatTime());
            supplierBalanceDetailMapper.insertSelective(sbean);
        }
        return supplierBalanceOweMapper.updateByPrimaryKeySelective(supplierBalanceOwe);
    }

    /**
     * 查询用户在供货商处的预存款
     * @param supplierBalanceOwe
     * @return
     */
    @Override
    public SupplierBalanceOwe selectbalance(SupplierBalanceOwe supplierBalanceOwe) {
        SupplierBalanceOwe supplierBalanceOwe1 = supplierBalanceOweMapper.selectOne(supplierBalanceOwe);
        if(supplierBalanceOwe1!=null){
            return supplierBalanceOwe1;
        }else {
            SupplierBalanceOwe supplierBalanceOwe2 = new SupplierBalanceOwe();
            supplierBalanceOwe2.setOwePrice(new BigDecimal(0));
            supplierBalanceOwe2.setPrePrice(new BigDecimal(0));
            return supplierBalanceOwe2;
        }
    }

}
