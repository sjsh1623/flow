package com.FlowApp.root;

import com.FlowApp.root.service.SelectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.FlowApp.root.model.*;

import java.util.*;

@Slf4j
@RestController
public class RestAPI {

    @Autowired
    SelectService selectService;

    /*
     * DB에 있는 모든 데이터를 가져옵니다
     * */
    @RequestMapping(value = "/ext_select", method = {RequestMethod.GET, RequestMethod.POST})
    /*파라미터를 받아오는 것 없이 리스트를 출력합니다*/
    public Map<String, Object> ext_select(Model model) {

        // DB에서 받아오는 객체입니다
        // 기존 차단 확장자
        List<addBeans> preExist = null;

        // 추가 차단 확장자
        List<addBeans> additional = null;

        try {
            // 기존 차단 확장자 DB 조회
            preExist = selectService.selectPreExist();

            // 추가 차단 확장자 DB 조회
            additional = selectService.selectAddition();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //JSON을 위해 MAP 객체 생성
        Map<String, Object> data = new HashMap<String, Object>();
        // 기존 차단 확장자 MAP에 추가
        data.put("preExist", preExist);
        // 추가 차단 확장자 MAP에 추가
        data.put("additional", additional);

        if (data != null) {
            data.putAll(data);
        } else {
            data.put("데이터가 존재하지 않습니다", "msg");
            data.putAll(data);
        }

        return data;
    }

    /*
     * 데이터를 추가를 진행하며 MYSQL에는 데이터가 unique로 되어있습니다
     * */
    @RequestMapping(value = "/ext_update", method = {RequestMethod.GET, RequestMethod.POST})
    /*파라미터를 받아오는 것 없이 리스트를 출력합니다*/
    public Map<String, Object> ext_update(Model model,
                                          @RequestParam(required = false) String[] preExist,
                                          @RequestParam(required = false) String[] addition) {

        //성공 여부를 판단하는 변수입니다.
        int preExistResult = 0;
        int additionResult = 0;
        try {
            // 만약 데이터가 없을시에 DB에 접속이 필요 없기때문에 확인하고 접속합니다
            if (preExist != null && preExist.length != 0) {
                // 업데이트에 성공시 1이상을 리턴합니다(업데이트 된 수)
                log.info("no");
                preExistResult = selectService.updatePreExist(preExist);
            } else {
                //체크가 하나도 없을 경우 모든 데이터 치크를 해제합니다
                preExistResult = selectService.updateAllPreExist();
            }

            // 만약 데이터가 없을시에 DB에 접속이 필요 없기때문에 확인하고 접속합니다
            if (addition != null && addition.length !=0) {
                // 추가 차단 확장자를 업데이트 하며 위와 같이 업데이트 성공시 1이상을 리턴합니다.
                additionResult = selectService.updateAddition(addition);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //JSON을 위해 MAP 객체 생성
        Map<String, Object> data = new HashMap<String, Object>();

        // 하나라도 성공했을 경우 해당 메세지를 출력
        if (preExistResult > 0 || additionResult > 0) {
            data.put("데이터 저장 성공", "msg");
        }
        return data;
    }


    @RequestMapping(value = "/ext_delete", method = {RequestMethod.GET, RequestMethod.POST})
    public Map<String, Object> ext_delete(Model model) {

        // 추가 차단 확장자 삭제여부를 확인하기 위한 변수입니다
        int result = 0;

        try {
            result = selectService.deleteAddition();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //JSON을 위해 MAP 객체 생성
        Map<String, Object> data = new HashMap<String, Object>();

        // 삭제 성공/실패를 출력
        if(result != 0) {
            data.put("데이터 저장 삭제", "msg");
        } else {
            data.put("데이터가 모두 삭제되었습니다", "msg");
        }

        return data;

    }
}