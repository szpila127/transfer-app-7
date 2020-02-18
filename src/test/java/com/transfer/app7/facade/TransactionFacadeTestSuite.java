package com.transfer.app7.facade;

import com.transfer.app7.client.NbpApiClient;
import com.transfer.app7.mapper.TransactionMapper;
import com.transfer.app7.service.AccountService;
import com.transfer.app7.service.TransactionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TransactionFacadeTestSuite {

    @InjectMocks
    private TransactionFacade transactionFacade;

    @Mock
    private AppEventFacade appEventFacade;

    @Mock
    private NbpApiClient nbpApiClient;

    @Mock
    private AccountService accountService;

    @Mock
    private TransactionService transactionService;

    @Mock
    private TransactionMapper transactionMapper;

    @Test
    public void test() {

    }
}
