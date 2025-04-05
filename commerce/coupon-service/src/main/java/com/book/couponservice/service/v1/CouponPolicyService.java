package com.book.couponservice.service.v1;

import com.book.couponservice.domain.CouponPolicy;
import com.book.couponservice.dto.v1.CouponPolicyDto;
import com.book.couponservice.exception.CouponPolicyNotFoundException;
import com.book.couponservice.repository.CouponPolicyRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CouponPolicyService {

  private final CouponPolicyRepository couponPolicyRepository;

  @Transactional
  public CouponPolicy createCouponPolicy(CouponPolicyDto.CreateRequest request) {
    CouponPolicy couponPolicy = request.toEntity();
    return couponPolicyRepository.save(couponPolicy);
  }

  @Transactional(readOnly = true)
  public CouponPolicy getCouponPolicy(Long id) {
    return couponPolicyRepository.findById(id)
        .orElseThrow(() -> new CouponPolicyNotFoundException("쿠폰 정책을 찾을 수 없습니다."));
  }

  @Transactional(readOnly = true)
  public List<CouponPolicy> getAllCouponPolicies() {
    return couponPolicyRepository.findAll();
  }
}
