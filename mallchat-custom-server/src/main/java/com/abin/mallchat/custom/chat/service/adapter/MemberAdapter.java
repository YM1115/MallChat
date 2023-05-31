package com.abin.mallchat.custom.chat.service.adapter;

import cn.hutool.core.lang.Pair;
import com.abin.mallchat.common.user.domain.entity.User;
import com.abin.mallchat.common.user.domain.enums.ChatActiveStatusEnum;
import com.abin.mallchat.common.user.service.cache.UserCache;
import com.abin.mallchat.custom.chat.domain.vo.response.ChatMemberResp;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Description: 成员适配器
 * Author: <a href="https://github.com/zongzibinbin">abin</a>
 * Date: 2023-03-26
 */
@Component
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MemberAdapter {
    private final UserCache userCache;

    public List<ChatMemberResp> buildMember(List<Pair<Long, Double>> list, ChatActiveStatusEnum statusEnum) {
        return list.stream().map(a -> {
            ChatMemberResp resp = new ChatMemberResp();
            resp.setActiveStatus(statusEnum.getStatus());
            resp.setLastOptTime(new Date(a.getValue().longValue()));
            resp.setUid(a.getKey());
            User userInfo = userCache.getUserInfo(a.getKey());
            resp.setName(userInfo.getName());
            resp.setAvatar(userInfo.getAvatar());
            return resp;
        }).collect(Collectors.toList());
    }
}
