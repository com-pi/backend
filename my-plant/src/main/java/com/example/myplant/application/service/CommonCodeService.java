package com.example.myplant.application.service;

import com.example.myplant.domain.CommonCode;
import com.example.myplant.adapter.out.repository.CommonCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommonCodeService {

    private final CommonCodeRepository commonCodeRepository;

    @Autowired
    public CommonCodeService(CommonCodeRepository commonCodeRepository){
        this.commonCodeRepository = commonCodeRepository;
    }

    public List<CommonCode> getCommonCodesByType(String codeType){
        return commonCodeRepository.findByCodeType(codeType);
    }

    public CommonCode addCommonCode(CommonCode commonCode) {
        return commonCodeRepository.save(commonCode);
    }

    public CommonCode updateCommonCode(Long id, CommonCode commonCodeDatails){
        CommonCode commonCode = commonCodeRepository.findById(id).orElseThrow(() -> new RuntimeException("CommonCode not found"));

        commonCode.setCodeType(commonCodeDatails.getCodeType());
        commonCode.setCodeValue(commonCodeDatails.getCodeValue());
        commonCode.setCodeName(commonCodeDatails.getCodeName());
        return commonCodeRepository.save(commonCode);
    }

    public void deleteCommonCode(Long id) {
        commonCodeRepository.deleteById(id);
    }
}
