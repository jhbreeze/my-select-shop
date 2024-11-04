package com.study.myselectshop.scheduler;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.study.myselectshop.entity.Product;
import com.study.myselectshop.naver.dto.ItemDto;
import com.study.myselectshop.naver.service.NaverApiService;
import com.study.myselectshop.repository.ProductRepository;
import com.study.myselectshop.service.ProductService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "Scheduler")
@Component
@RequiredArgsConstructor
public class Scheduler {

	private final NaverApiService naverApiService;
	private final ProductService productService;
	private final ProductRepository productRepository;

	// 순서: 초 분 시 일 월 주
	@Scheduled(cron = "0 0 1 * * *") // 매일 새벽 1시
	public void updatePrice() throws InterruptedException {
		log.info("최저가 업데이트 스케줄러 실행");
		List<Product> products = productRepository.findAll();

		for (Product product : products) {
			TimeUnit.SECONDS.sleep(1); 	// 1초에 한 번씩 상품 조회

			List<ItemDto> itemDtos = naverApiService.searchItems(product.getTitle());

			if(itemDtos.size() > 0) {
				ItemDto itemDto = itemDtos.get(0);
				Long id = product.getId();
				try {
					productService.updateBySearch(id, itemDto);
				} catch (Exception e) {
					log.error(id + " : " + e.getMessage());
				}
			}
		}
	}

}
