package com.cherry.jeeves.event;

import com.cherry.jeeves.domain.shared.RecommendInfo;
import org.springframework.context.ApplicationEvent;

/**
 * 加好友邀请信息事件
 *
 * @author tangjialin on 2018-08-08.
 */
public class FriendInvitationMessageEvent extends ApplicationEvent {
    private RecommendInfo recommendInfo;

    /**
     * @param source        事件源
     * @param recommendInfo 邀请信息
     */
    public FriendInvitationMessageEvent(Object source, RecommendInfo recommendInfo) {
        super(source);
        this.recommendInfo = recommendInfo;
    }

    public RecommendInfo getRecommendInfo() {
        return recommendInfo;
    }

}
