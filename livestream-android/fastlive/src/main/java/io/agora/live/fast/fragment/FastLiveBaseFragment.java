package io.agora.live.fast.fragment;

import android.os.Bundle;
import android.util.Log;

import io.agora.live.FastLiveHelper;
import io.agora.live.rtc.RtcEventHandler;
import io.agora.live.widgets.VideoGridContainer;
import io.agora.rtc2.Constants;

public abstract class FastLiveBaseFragment extends FastBaseFragment implements RtcEventHandler {
    protected String channel;
    protected String roomId;
    protected String hxId;
    protected String hxAppkey;
    protected String rtcToken;
    protected int role = Constants.CLIENT_ROLE_AUDIENCE;
    protected int uid = 0;
    protected FastLiveHelper helper;
    public VideoGridContainer mVideoGridContainer;
    protected boolean preLeave;

    @Override
    protected void initArgument() {
        super.initArgument();
        Bundle bundle = getArguments();
        if (bundle != null) {
            channel = bundle.getString("channel");
            roomId = bundle.getString("roomId");
            hxId = bundle.getString("hxId");
            hxAppkey = bundle.getString("hxAppkey");
        }
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        helper = FastLiveHelper.getInstance();
        helper.registerRtcHandler(this);
        helper.getEngineConfig().setChannelName(channel);
    }

    @Override
    protected void initListener() {
        super.initListener();
    }

    @Override
    protected void initData() {
        super.initData();
//        if(BuildConfig.DEBUG) {
//            joinRtcChannel(null);
//        }else {
        getAgoraToken();
//        }
        initRtc();
    }

    public abstract void getAgoraToken();

    private void initRtc() {
        mVideoGridContainer.setStatsManager(FastLiveHelper.getInstance().getStatsManager());
    }

    @Override
    public void onRtcConnectionStateChanged(int state, int reason) {
        Log.i("fast", "onRtcConnectionStateChanged state: " + state + " reason: " + reason);
        if (reason == Constants.CONNECTION_CHANGED_INVALID_TOKEN) {
            Log.i("fast", "connection_changed_invalid_token");
        } else if (reason == Constants.CONNECTION_CHANGED_TOKEN_EXPIRED) {
            onTokenExpired();
        }
    }

    @Override
    public void onRtcTokenPrivilegeWillExpire(String token) {
        onTokenPrivilegeWillExpire(token);
    }


    protected abstract void onTokenPrivilegeWillExpire(String token);

    protected abstract void onTokenExpired();

    public void joinRtcChannel(String token) {
        Log.i("fast", "joinRtcChannel");
        helper.joinRtcChannel(role, token, uid);
    }

    public void renewToken(String token) {
        helper.renewRtcToken(token);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i("fast", "onDestroyView");
        if (!preLeave) {
            helper.onDestroy(this);
        }
    }
}

