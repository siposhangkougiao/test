package com.mtnz.service.system.product;

import com.mtnz.dao.DaoSupport;
import com.mtnz.entity.Page;
import com.mtnz.util.PageData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/*
    Created by xxj on 2018\3\21 0021.
 */

@Service("productService")
public class ProductService {

    @Resource(name = "daoSupport")
    private DaoSupport daoSupport;

    public void save(PageData pd) throws Exception {
        daoSupport.save("ProductMapper.save",pd);
    }

    public PageData findById(PageData pd) throws Exception {
        return (PageData) daoSupport.findForObject("ProductMapper.findById",pd);
    }

    public List<PageData> findProduct(PageData pd) throws Exception {
        return (List<PageData>) daoSupport.findForList("ProductMapper.findProduct",pd);
    }

    public PageData findProductCount(PageData pd) throws Exception {
        return (PageData) daoSupport.findForObject("ProductMapper.findProductCount",pd);
    }

    public void updateProduct(PageData pd) throws Exception {
        daoSupport.update("ProductMapper.updateProduct",pd);
    }

    public void delete(PageData pd) throws Exception {
        daoSupport.delete("ProductMapper.delete",pd);
    }

    public List<PageData> findlikeList(PageData pd) throws Exception {
        return (List<PageData>) daoSupport.findForList("ProductMapper.findlikeList",pd);
    }

    public void editNum(PageData pd) throws Exception {
        daoSupport.update("ProductMapper.editNum",pd);
    }

    public void editNumJia(PageData pd) throws Exception {
        daoSupport.update("ProductMapper.editNumJia",pd);
    }

    public void editNums(PageData pd) throws Exception {
        daoSupport.update("ProductMapper.editNums",pd);
    }

    public List<PageData> findProductProfit(PageData pd) throws Exception {
        return (List<PageData>) daoSupport.findForList("ProductMapper.findProductProfit",pd);
    }

/*    public List<PageData> findProductProfitList(PageData pd) throws Exception {
        return (List<PageData>) daoSupport.findForList("ProductMapper.findProductProfitList",pd);
    }*/

    public PageData findBarCodeProduct(PageData pd) throws Exception {
        return (PageData) daoSupport.findForObject("ProductMapper.findBarCodeProduct",pd);
    }

    public List<PageData> findBarCodeProducts(PageData pd) throws Exception {
        return (List<PageData>) daoSupport.findForList("ProductMapper.findBarCodeProducts",pd);
    }

    public PageData findKuCun(PageData pd) throws Exception {
        return (PageData) daoSupport.findForObject("ProductMapper.findKuCun",pd);
    }

    public List<PageData> findListType(PageData pd) throws Exception {
        return (List<PageData>) daoSupport.findForList("ProductMapper.findListType",pd);
    }

    public void editSupplierId(PageData pd) throws Exception {
        daoSupport.update("ProductMapper.editSupplierId",pd);
    }

    public List<PageData> findSupplierList(PageData pd) throws Exception {
        return (List<PageData>) daoSupport.findForList("ProductMapper.findSupplierList",pd);
    }

    public PageData findCount(PageData pd) throws Exception {
        return (PageData) daoSupport.findForObject("ProductMapper.findCount", pd);
    }

    public List<PageData> findNotSupplierList(Page page) throws Exception {
        return (List<PageData>) daoSupport.findForList("ProductMapper.SupplierdatalistPage",page);
    }

    public List<PageData> findLikeSupplierProduct(PageData pd) throws Exception {
        return (List<PageData>) daoSupport.findForList("ProductMapper.findLikeSupplierProduct",pd);
    }


    public void editStatus(PageData pd) throws Exception {
        daoSupport.update("ProductMapper.editStatus",pd);
    }

    public PageData findByNumber(PageData pd) throws Exception {
        return (PageData) daoSupport.findForObject("ProductMapper.findByNumber",pd);
    }

    public List<PageData> findByNumbers(PageData pd) throws Exception {
        return (List<PageData>) daoSupport.findForList("ProductMapper.findByNumbers",pd);
    }


    public List<PageData> findProductFenXi(PageData pd) throws Exception {
        return (List<PageData>) daoSupport.findForList("ProductMapper.findProductFenXi",pd);
    }

    public void editJianNums(PageData pd) throws Exception {
        daoSupport.update("ProductMapper.editJianNums",pd);
    }

    public void editJiaNums(PageData pd) throws Exception {
        daoSupport.update("ProductMapper.editJiaNums",pd);
    }


    public List<PageData> findSupplierFeiXi(PageData pd) throws Exception {
        return (List<PageData>) daoSupport.findForList("ProductMapper.findSupplierFeiXi",pd);
    }

    public List<PageData> findReportAnalysis(PageData pd) throws Exception {
        return (List<PageData>) daoSupport.findForList("ProductMapper.findReportAnalysis",pd);
    }

    public List<PageData> findReportAnalysisYue(PageData pd) throws Exception {
        return (List<PageData>) daoSupport.findForList("ProductMapper.findReportAnalysisYue",pd);
    }

    public List<PageData> findReportAnalysisXiaoShi(PageData pd) throws Exception {
        return (List<PageData>) daoSupport.findForList("ProductMapper.findReportAnalysisXiaoShi",pd);
    }

    public PageData findSumProductMoney(PageData pd) throws Exception {
        return (PageData) daoSupport.findForObject("ProductMapper.findSumProductMoney",pd);
    }

    public List<PageData> findProductFeiXis(PageData pd) throws Exception {
        return (List<PageData>) daoSupport.findForList("ProductMapper.findProductFeiXis",pd);
    }


    public List<PageData> findProductPrice(PageData pc) throws Exception {

        return (List<PageData>) daoSupport.findForList("ProductMapper.findProductPrice",pc);
    }

    public void editNumli(PageData pd) throws Exception {

        daoSupport.update("ProductMapper.editNumli",pd);
    }

    public void editJiaNumsli(PageData pd) throws Exception {
        daoSupport.update("ProductMapper.editJiaNumsli",pd);
    }

    public void editJianNumsli(PageData pd) throws Exception {
        daoSupport.update("ProductMapper.editJianNumsli",pd);
    }

    public void editNumslikucun(PageData pd) throws Exception {
        daoSupport.update("ProductMapper.editNumslikucun",pd);
    }

    public List<PageData> selectProductLevel(PageData pd) throws Exception {
        return (List<PageData>) daoSupport.findForList("ProductMapper.selectProductLevel",pd);
    }

    public PageData selectLevel(PageData pd) throws Exception {
        return (PageData) daoSupport.findForObject("ProductMapper.selectLevel",pd);
    }

    public PageData selectSaleLevel(PageData pd) throws Exception {
        return (PageData) daoSupport.findForObject("ProductMapper.selectSaleLevel",pd);
    }

    public PageData findSaleLevel(PageData pd) throws Exception {
        return (PageData) daoSupport.findForObject("ProductMapper.findSaleLevel",pd);
    }
}
