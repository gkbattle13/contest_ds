package com.incar.contest.service;

import com.incar.contest.bean.BasicStock;
import com.incar.contest.repository.BasicStockRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 基础数据
 *
 * @author GuoKun
 * @version 1.0
 * @create_date 2020/2/28 16:27
 */
@Service
public class BasicStockService {

    @Resource
    private BasicStockRepository roleRepository;


    public List<BasicStock> findAll() {
        return roleRepository.findAll();
    }
}
