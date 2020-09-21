package com.example.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PolicyHandler {

//    @StreamListener(Processor.INPUT)
//    public void onEventByString(@Payload String productChanged){
//        System.out.println(productChanged);
//    }

    @Autowired
    ProductRepository productRepository;

    @StreamListener(Processor.INPUT)
    public void onEventByObject(@Payload OrderPlaced orderPlaced){
        // orderPlaced 데이터를 json -> 객체로 파싱 -> 해결

        // if (주문이 생성되었을때만)
        if( "OrderPlaced".equals(orderPlaced.getEventType())){
            // 상품저장
//            Product p = new Product();
//            p.setStock(orderPlaced.getQty());
//            productRepository.save(p);

            // 상품 ID 값의 재고 변경
            Optional<Product> productById = productRepository.findById(orderPlaced.getProductId());

            Product p = productById.get();
            p.setStock( p.getStock() - orderPlaced.getQty());
            productRepository.save(p);
        }

    }
}
