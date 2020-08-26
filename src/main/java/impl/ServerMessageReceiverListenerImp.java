package impl;

import lombok.RequiredArgsConstructor;
import org.apache.log4j.Logger;
import org.jsmpp.InvalidResponseException;
import org.jsmpp.PDUException;
import org.jsmpp.bean.*;
import org.jsmpp.extra.NegativeResponseException;
import org.jsmpp.extra.ProcessRequestException;
import org.jsmpp.extra.ResponseTimeoutException;
import org.jsmpp.session.*;
import org.jsmpp.util.MessageIDGenerator;
import org.jsmpp.util.MessageId;
import org.jsmpp.util.RandomMessageIDGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;


public class ServerMessageReceiverListenerImp implements ServerMessageReceiverListener {
    static final Logger LOGGER = Logger.getLogger(ServerMessageReceiverListenerImp.class);
    public static AtomicLong messageCount = new AtomicLong(0);
    final MessageIDGenerator messageIdGenerator = new RandomMessageIDGenerator();
    private ExecutorService executorService;
    Timer timer = new Timer();

    public void init() {
    }

    @Override
    public MessageId onAcceptSubmitSm(SubmitSm submitSm, SMPPServerSession smppServerSession) throws ProcessRequestException {
//
        messageCount.getAndIncrement();
        try {
            RegisteredDelivery registeredDelivery = new RegisteredDelivery();
            String message = "Message is delivery";

            smppServerSession.deliverShortMessage("CMT", TypeOfNumber.INTERNATIONAL,
                        NumberingPlanIndicator.UNKNOWN, submitSm.getDestAddress(), TypeOfNumber.INTERNATIONAL, NumberingPlanIndicator.UNKNOWN,
                        submitSm.getSourceAddr(), new ESMClass(), (byte)0, (byte)1,
                        registeredDelivery,  new GeneralDataCoding(Alphabet.ALPHA_DEFAULT, MessageClass.CLASS1,
                                false), message.getBytes());

//            LOGGER.info(new String(submitSm.getShortMessage())+" "+messageCount);
        } catch (PDUException e) {
            e.printStackTrace();
        } catch (ResponseTimeoutException e) {
            e.printStackTrace();
        } catch (InvalidResponseException e) {
            e.printStackTrace();
        } catch (NegativeResponseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // need message_id to response submit_sm
            return messageIdGenerator.newMessageId();
    }

    @Override
    public SubmitMultiResult onAcceptSubmitMulti(SubmitMulti submitMulti, SMPPServerSession smppServerSession) throws ProcessRequestException {
        return null;
    }

    @Override
    public QuerySmResult onAcceptQuerySm(QuerySm querySm, SMPPServerSession smppServerSession) throws ProcessRequestException {
        return null;
    }

    @Override
    public void onAcceptReplaceSm(ReplaceSm replaceSm, SMPPServerSession smppServerSession) throws ProcessRequestException {

    }

    @Override
    public void onAcceptCancelSm(CancelSm cancelSm, SMPPServerSession smppServerSession) throws ProcessRequestException {
    }

    @Override
    public DataSmResult onAcceptDataSm(DataSm dataSm, Session session) throws ProcessRequestException {
        return null;
    }

    public synchronized void tpsScheduler() {
        ScheduledExecutorService ses = Executors.newScheduledThreadPool(1);
        ses.scheduleAtFixedRate(() -> System.out.println(messageCount.getAndSet(0)), 1, 1, TimeUnit.SECONDS);
    }

}
