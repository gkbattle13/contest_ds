package com.incar.contest_ds.repository;

import com.incar.contest_ds.bean.BasicStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 基础数据
 *
 * @author GuoKun
 * @version 1.0
 * @create_date 2020/2/28 16:28
 */
@Repository
public interface BasicStockRepository extends JpaRepository<BasicStock,String> {

}
