package com.shop.service;

import com.shop.dto.OrderDto;
import com.shop.dto.OrderHistDto;
import com.shop.dto.OrderItemDto;
import com.shop.entity.*;
import com.shop.repository.ItemImgRepository;
import com.shop.repository.ItemRepository;
import com.shop.repository.MemberRepository;
import com.shop.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@Log4j2
@RequiredArgsConstructor
public class OrderService {

    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;
    private final OrderRepository orderRepository;
    private final ItemImgRepository itemImgRepository;

    public Long order(OrderDto orderDto, String email){
        Item item = itemRepository.findById(orderDto.getItemId())
                .orElseThrow(EntityNotFoundException::new);

        Member member = memberRepository.findByEmail(email);

        List<OrderItem> orderItemList = new ArrayList<>();

        OrderItem orderItem = OrderItem.createOrderItem(item, orderDto.getCount());

        orderItemList.add(orderItem);

        Order order = Order.createOrder(member, orderItemList);

        orderRepository.save(order);

        return order.getId();
    }


    /*
        이메일에 해당하는 사용자의 주문 목록을 조회하고,
        각 주문에 대해 필요한 정보를 DTO로 변환하여 페이징된 형태로 반환합니다.
        이를 통해 호출자는 사용자의 주문 이력을 엑세스 한다.
     */
    @Transactional(readOnly = true)
    public Page<OrderHistDto> getOrderList(String email, Pageable pageable){
        
        // 아이디와 페이징조건을 이용하여 주문 목록 조회 
        List<Order> orders = orderRepository.findOrders(email, pageable);
        // 유저 주문 개수 조회
        Long totalCount = orderRepository.countOrder(email);

        List<OrderHistDto> orderHistDtos = new ArrayList<>();

        //주문리스트 순회하면서 구매 이력 페이지에 전달할 DTO 생성
        
        for(Order order : orders){
            OrderHistDto orderHistDto = new OrderHistDto(order);

            List<OrderItem> orderItems = order.getOrderItems(); //해당 주문의 주문 항목 리스트를 가져온다.
            for(OrderItem orderItem : orderItems){
                //주문 항목의 대표 이미지를 조회
                ItemImg itemImg = itemImgRepository.findByItemIdAndRepimgYn(orderItem.getItem().getId(), "Y");
                //각 주문 항목에 대해 OrderItemDto 객체를 생성
                OrderItemDto orderItemDto = new OrderItemDto(orderItem, itemImg.getImgUrl());
                //생성된 OrderItemDto를 OrderHistDto에 추가
                orderHistDto.addOrderItemDto(orderItemDto);
            }
            //OrderHistDto를 리스트에 추가
            orderHistDtos.add(orderHistDto);
        }

        // 조회된 OrderHistDto 객체들의 리스트,  페이징 정보,  전체 주문 수
        return new PageImpl<OrderHistDto>(orderHistDtos, pageable, totalCount);
    }

}
