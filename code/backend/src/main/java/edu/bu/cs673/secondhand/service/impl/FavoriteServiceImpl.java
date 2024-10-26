package edu.bu.cs673.secondhand.service.impl;

import edu.bu.cs673.secondhand.domain.Favorite;
import edu.bu.cs673.secondhand.domain.FavoriteExample;
import edu.bu.cs673.secondhand.domain.IdleItem;
import edu.bu.cs673.secondhand.domain.IdleItemExample;
import edu.bu.cs673.secondhand.mapper.FavoriteMapper;
import edu.bu.cs673.secondhand.mapper.IdleItemMapper;
import edu.bu.cs673.secondhand.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/***
 Email: ybinman@bu.edu
 DateTime: 10/26/24-13:12
 *****/
@Service
public class FavoriteServiceImpl implements FavoriteService {

    @Autowired
    FavoriteMapper favoriteMapper;

    @Autowired
    IdleItemMapper idleItemMapper;

    @Override
    public boolean addFavorite(Favorite favoriteModel) {
        return favoriteMapper.insert(favoriteModel)==1;
    }

    @Override
    public boolean deleteFavorite(Long id) {
        return favoriteMapper.deleteByPrimaryKey(id)==1;
    }

    @Override
    public Integer isFavorite(Long userId, Long idleId) {
        FavoriteExample favoriteExample = new FavoriteExample();
        FavoriteExample.Criteria criteria = favoriteExample.createCriteria();
        criteria.andUserIdEqualTo(userId);
        criteria.andIdleIdEqualTo(idleId);
        List<Favorite> ret = favoriteMapper.selectByExample(favoriteExample);
        return ret.size();
    }

    @Override
    public List<Favorite> getAllFavorite(Long userId) {
        FavoriteExample favoriteExample = new FavoriteExample();
        FavoriteExample.Criteria criteria = favoriteExample.createCriteria();
        criteria.andUserIdEqualTo(userId);
        List<Favorite> list = favoriteMapper.selectByExample(favoriteExample);

        if(list.size()>0){
            List<Long> idleIdList=new ArrayList<>();
            for(Favorite i:list){
                idleIdList.add(i.getIdleId());
            }

            IdleItemExample idleItemExample = new IdleItemExample();
            IdleItemExample.Criteria criteria1 = idleItemExample.createCriteria();
            criteria1.andIdIn(idleIdList);
            List<IdleItem> idleItemModelList=idleItemMapper.selectByExample(idleItemExample);
            Map<Long,IdleItem> map=new HashMap<>();

            for(IdleItem idle:idleItemModelList){
                map.put(idle.getId(),idle);
            }
            for(Favorite i:list){
                i.setIdleItem(map.get(i.getIdleId()));
            }
        }
        return list;
    }
}
