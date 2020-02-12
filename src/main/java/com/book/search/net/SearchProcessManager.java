package com.book.search.net;

import com.book.search.common.data.BookResultData;
import com.book.search.common.data.StoreTranslate;
import com.book.search.exception.biz.NotFoundException;
import com.book.search.net.kakao.KakaoSearchBookProcess;
import com.book.search.net.naver.NaverSearchBookProcess;
import com.book.search.support.ChainHandler;
import com.book.search.support.HandlerChain;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SearchProcessManager implements InitializingBean, HandlerChain<StoreTranslate, BookResultData> {

    @Autowired
    KakaoSearchBookProcess kakaoSearchBookProcess;

    @Autowired
    NaverSearchBookProcess naverSearchBookProcess;

    ChainHandler<StoreTranslate, BookResultData> chain;

    private void buildChain() {
        chain = kakaoSearchBookProcess;
        chain.add(naverSearchBookProcess);
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
