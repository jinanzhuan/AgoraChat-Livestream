//
//  ELDUserInfoModel.m
//  EaseMobLiveDemo
//
//  Created by liu001 on 2022/4/11.
//  Copyright © 2022 zmw. All rights reserved.
//

#import "ELDUserInfoModel.h"
@interface ELDUserInfoModel ()
@property (nonatomic, strong) NSString *hyphenateId;
@property(nonatomic, strong)AgoraChatUserInfo *userInfo;

@end

@implementation ELDUserInfoModel

- (instancetype)initWithUserInfo:(AgoraChatUserInfo *)userInfo {
    self = [super init];
    if (self) {
        self.userInfo = userInfo;
        self.hyphenateId = self.userInfo.userId;
    }
    return self;

}


- (NSString *)displayName {
    return self.userInfo.nickName ?:self.userInfo.userId;
}


@end
