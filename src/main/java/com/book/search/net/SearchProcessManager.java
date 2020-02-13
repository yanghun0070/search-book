package com.book.search.net;

import com.book.search.common.data.BookResultData;
import com.book.search.common.data.StoreTranslate;
import com.book.search.exception.biz.NotFoundException;
import com.book.search.net.kakao.KakaoSearchBookService;
import com.book.search.net.naver.NaverSearchBookService;
import com.book.search.support.ChainHandler;
import com.book.search.support.HandlerChain;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * chain형태로 구성된 beans를 관리하는 클래스
 */
@Component
public class SearchProcessManager implements InitializingBean, HandlerChain<StoreTranslate, BookResultData> {

    @Autowired
    KakaoSearchBookService kakaoSearchBookService;

    @Autowired
    NaverSearchBookService naverSearchBookService;

    ChainHandler<StoreTranslate, BookResultData> chain;

    private void buildChain() {
        chain = kakaoSearchBookService;
        chain.add(naverSearchBookService);
    }

    @Override
    public void afterPropertiesSet() {
        buildChain();
    }

    @Override
    public BookResultData makeRequest(StoreTranslate request) throws NotFoundException {
        return chain.handleRequest(request);
    }
}
