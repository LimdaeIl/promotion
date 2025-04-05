package com.book.couponservice.controller.v1;

import com.book.couponservice.domain.Coupon;
import com.book.couponservice.dto.v1.CouponDto;
import com.book.couponservice.dto.v1.CouponDto.Response;
import com.book.couponservice.service.v1.CouponService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/coupons")
@RequiredArgsConstructor
public class CouponController {

  private final CouponService couponService;

  @PostMapping("/issue")
  public ResponseEntity<Response> issueCoupon(@RequestBody CouponDto.IssueRequest request) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(CouponDto.Response.from(couponService.issueCoupon(request)));
  }

  @PostMapping("/{couponId}/use")
  public ResponseEntity<CouponDto.Response> useCoupon(
      @PathVariable Long couponId,
      @RequestBody CouponDto.UseRequest request) {
    return ResponseEntity.ok(CouponDto.Response.from(couponService.useCoupon(couponId, request.getOrderId())));
  }

  @PostMapping("/{couponId}/cancel")
  public ResponseEntity<CouponDto.Response> cancelCoupon(@PathVariable Long couponId) {
    return ResponseEntity.ok(CouponDto.Response.from(couponService.cancelCoupon(couponId)));
  }

  @GetMapping
  public ResponseEntity<List<CouponDto.Response>> getCoupons(
      @RequestParam(required = false) Coupon.Status status,
      @RequestParam(required = false) Integer page,
      @RequestParam(required = false) Integer size) {

    CouponDto.ListRequest request = CouponDto.ListRequest.builder()
        .status(status)
        .page(page)
        .size(size)
        .build();

    return ResponseEntity.ok(couponService.getCoupons(request));
  }
}