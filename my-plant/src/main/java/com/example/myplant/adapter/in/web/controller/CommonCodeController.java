//package com.example.myplant.adapter.in.web.controller;
//
//import com.example.myplant.domain.CommonCode;
//import com.example.myplant.application.service.CommonCodeService;
//import com.example.common.baseentity.CommonResponse;
//import com.example.common.annotation.Authenticate;
//import com.example.common.domain.Role;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/common-codes")
//public class CommonCodeController {
//
//    private final CommonCodeService commonCodeService;
//
//    @Autowired
//    public CommonCodeController(CommonCodeService commonCodeService){
//        this.commonCodeService = commonCodeService;
//    }
//
//    @Authenticate(Role.ADMIN)
//    @GetMapping("/{codeType}")
//    public ResponseEntity<CommonResponse<List<CommonCode>>> getCommonCodesByType(@PathVariable String codeType) {
//        List<CommonCode> commonCodes = commonCodeService.getCommonCodesByType(codeType);
//        return ResponseEntity.ok(new CommonResponse<>("Common codes retrieved successfully", commonCodes));
//    }
//
//    @Authenticate(Role.ADMIN)
//    @PostMapping
//    public ResponseEntity<CommonResponse<CommonCode>> addCommonCode(@RequestBody CommonCode commonCode) {
//        CommonCode newCommonCode = commonCodeService.addCommonCode(commonCode);
//        return ResponseEntity.ok(new CommonResponse<>("Common code added successfully", newCommonCode));
//    }
//
//    @Authenticate(Role.ADMIN)
//    @PutMapping("/{id}")
//    public ResponseEntity<CommonResponse<CommonCode>> updateCommonCode(@PathVariable Long id, @RequestBody CommonCode commonCodeDetails) {
//        CommonCode updatedCommonCode = commonCodeService.updateCommonCode(id, commonCodeDetails);
//        return ResponseEntity.ok(new CommonResponse<>("Common code updated successfully", updatedCommonCode));
//    }
//
//    @Authenticate(Role.ADMIN)
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteCommonCode(@PathVariable Long id) {
//        commonCodeService.deleteCommonCode(id);
//        return ResponseEntity.noContent().build();
//    }
//}
