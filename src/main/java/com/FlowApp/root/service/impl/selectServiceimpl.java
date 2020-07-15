package com.FlowApp.root.service.impl;

import com.FlowApp.root.model.addBeans;
import com.FlowApp.root.service.SelectService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class selectServiceimpl implements SelectService {

    @Autowired
    SqlSession sqlSession;

    @Override
    public List<addBeans> selectPreExist() throws Exception {
        List<addBeans> result = null;
        try {
            result = sqlSession.selectList("extMapper.getPreExist");
        } catch (Exception e) {
            throw new Exception("데이터 조회에 실패했습니다.");
        }
        return result;
    }

    @Override
    public List<addBeans> selectAddition() throws Exception {
        List<addBeans> result = null;
        try {
            result = sqlSession.selectList("extMapper.getAddition");
        } catch (Exception e) {
            throw new Exception("데이터 조회에 실패했습니다.");
        }
        return result;
    }

    @Override
    public int updateAllPreExist() throws Exception {
        int result = 0;
        try {
            // 데이터를 업데이트 합니다
            result = sqlSession.update("extMapper.updateResetAll");

        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
            throw new Exception("데이터 수정에 실패했습니다.");
        }
        return result;
    }

    @Override
    public int updatePreExist(String[] input) throws Exception {
        int result = 0;
        try {
            // 초기화를 진행함으로 체크박스에 내용이 달라질 때마다 새롭게 업데이트 됩니다
            result = sqlSession.update("extMapper.updateReset", input);

            // 데이터를 업데이트 합니다
            result = sqlSession.update("extMapper.updatePreExist", input);

        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
            throw new Exception("데이터 수정에 실패했습니다.");
        }
        return result;
    }

    @Override
    public int updateAddition(String[] input) throws Exception {
        int result = 0;
        try {
            // 데이터를 업데이트 합니다
            result = sqlSession.insert("extMapper.updateAddition", input);

        } catch (Exception e) {
            result = -1; //데이터는 unique하기 때문에 중복된다면 -1를 리턴한다.
        }
        return result;
    }

    @Override
    public int deleteAddition() throws Exception {
        int result = 0;

        try {
            result = sqlSession.delete("extMapper.deleteAddition");
        } catch (NullPointerException e) {
            log.error(e.getLocalizedMessage());
            throw new Exception("수정된 데이터가 없습니다.");
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
            throw new Exception("데이터 수정에 실패했습니다.");
        }

        return result;
    }

}
