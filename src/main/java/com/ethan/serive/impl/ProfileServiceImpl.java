package com.ethan.serive.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ethan.dao.ProfileMapper;
import com.ethan.entity.Profile;
import com.ethan.serive.ProfileService;
import org.springframework.stereotype.Service;

/**
 * Created by ASUS on 2020/2/4.
 */
@Service
public class ProfileServiceImpl extends ServiceImpl<ProfileMapper, Profile> implements ProfileService {

}
