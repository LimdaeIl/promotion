package com.book.couponservice.service.v2;

import com.book.couponservice.domain.Coupon;
import com.book.couponservice.dto.v2.CouponDto;
import com.book.couponservice.exception.CouponNotFoundException;
import com.book.couponservice.repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("couponServiceV2")
@RequiredArgsConstructor
public class CouponService {

  private final CouponRepository couponRepository;
  private final CouponRedisService couponRedisService;
  private final CouponStateService couponStateService;

  @Transactional
  public CouponDto.CouponResponse issueCoupon(CouponDto.CouponIssueRequest request) {
    Coupon coupon = couponRedisService.issueCoupon(request);
    couponStateService.updateCouponState(couponRepository.findById(coupon.getId())
        .orElseThrow(() -> new CouponNotFoundException("쿠폰을 찾을 수 없습니다.")));
    return CouponDto.CouponResponse.from(coupon);
  }

  @Transactional
  public CouponDto.CouponResponse useCoupon(Long couponId, Long orderId) {
    Coupon coupon = couponRepository.findByIdWithLock(couponId)
        .orElseThrow(() -> new CouponNotFoundException("쿠폰을 찾을 수 없습니다."));

    coupon.use(orderId);
    couponStateService.updateCouponState(coupon);

    return CouponDto.CouponResponse.from(coupon);
  }

  @Transactional
  public CouponDto.CouponResponse cancelCoupon(Long couponId) {
    Coupon coupon = couponRepository.findByIdWithLock(couponId)
        .orElseThrow(() -> new CouponNotFoundException("쿠폰을 찾을 수 없습니다."));

    coupon.cancel();
    couponStateService.updateCouponState(coupon);

    return CouponDto.CouponResponse.from(coupon);
  }

  public CouponDto.CouponResponse getCoupon(Long couponId) {
    CouponDto.CouponResponse cachedCoupon = couponStateService.getCouponState(couponId);
    if (cachedCoupon != null) {
      return cachedCoupon;
    }

    Coupon coupon = couponRepository.findById(couponId)
        .orElseThrow(() -> new CouponNotFoundException("쿠폰을 찾을 수 없습니다."));

    CouponDto.CouponResponse response = CouponDto.CouponResponse.from(coupon);
    couponStateService.updateCouponState(coupon);

    return response;
  }
}
