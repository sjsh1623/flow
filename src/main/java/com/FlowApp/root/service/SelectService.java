package com.FlowApp.root.service;

import com.FlowApp.root.model.addBeans;

import java.util.List;

public interface SelectService {

    /**
     * 모든 체크된 기존 확장자 가져오기
     * */
    public List<addBeans> selectPreExist() throws Exception;


    /**
     * 모든 추가 확장자 가져오기
     * */
    public List<addBeans> selectAddition() throws Exception;


    /**
     * 기존 확장자 업데이트
     * */
    public int updateAllPreExist() throws Exception;


    /**
     * 기존 확장자 업데이트
     * */
    public int updatePreExist(String[] input) throws Exception;

    /**
     * 추가 확장자 추가
     * */
    public int updateAddition(String[] input) throws Exception;

    /**
     * 모든 추가 확장자 삭제
     * */
    public int deleteAddition() throws Exception;
}