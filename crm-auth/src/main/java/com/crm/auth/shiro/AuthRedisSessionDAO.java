package com.crm.auth.shiro;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.SimpleSession;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.Serializable;
import java.util.Collection;

/**
 * sesiion 存储
 * @author huhong
 * @date 2019-06-22 15:39
 */
public class AuthRedisSessionDAO extends AbstractSessionDAO {
    Log log = LogFactory.getLog(AuthRedisSessionDAO.class);
    private final String redisKey = "crm:auth:sessionId";

    @Autowired
    RedisTemplate redisTemplate;

    @Override
    protected Serializable doCreate(Session session) {

        Serializable id = super.generateSessionId(session);
        ((SimpleSession)session).setId(id);
        log.debug("doCreate session:" + id);
        redisTemplate.opsForHash().put(redisKey, id, session);
        return id;
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        Session session = (Session)redisTemplate.opsForHash().get(redisKey,sessionId);
        log.debug("doReadSession session:" + sessionId);
        return session;
    }

    @Override
    public void update(Session session) throws UnknownSessionException {
        Serializable id = session.getId();
        if(id == null){
            throw new NullPointerException();
        }
        redisTemplate.opsForHash().put(redisKey, id, session);
    }

    @Override
    public void delete(Session session) {
        Serializable id = session.getId();
        redisTemplate.opsForHash().delete(redisKey, id);
    }

    @Override
    public Collection<Session> getActiveSessions() {
        return redisTemplate.opsForHash().values(redisKey);
    }
}
