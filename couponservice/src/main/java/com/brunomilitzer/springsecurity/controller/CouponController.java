package com.brunomilitzer.springsecurity.controller;

import com.brunomilitzer.springsecurity.model.Coupon;
import com.brunomilitzer.springsecurity.repositories.CouponRepository;
import javax.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@CrossOrigin
public class CouponController {

    @Autowired
    private CouponRepository repository;

    @GetMapping("/showCreateCoupon")
    @PreAuthorize("hasRole('ADMIN')")
    //@RolesAllowed("ADMIN")
    public String showCreateCoupon() {

        return "createCoupon";
    }

    @PostMapping("/saveCoupon")
    public String save(Coupon coupon) {
        repository.save(coupon);

        return "createResponse";
    }

    @GetMapping("/showGetCoupon")
    public String showGetCoupon() {

        return "getCoupon";
    }

    @PostMapping("/getCoupon")
    public ModelAndView getCoupon(String code) {
        ModelAndView couponDetails = new ModelAndView("couponDetails");
        couponDetails.addObject(repository.findByCode(code));
        return couponDetails;
    }
}
