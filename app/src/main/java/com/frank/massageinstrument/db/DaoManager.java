package com.frank.massageinstrument.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.frank.massageinstrument.entity.AcupointBean;
import com.frank.massageinstrument.entity.AcupointParamBean;

import java.util.List;


/**
 * Created by huliuda on 17-7-10.
 */

public class DaoManager {

    final static String filename = "web.db";
    final static String pathStr = "/data/data/com.frank.massageinstrument/databases";
    private static DaoManager mDbManager;
    private static MyOpenHelper mDevOpenHelper;
    private static DaoMaster mDaoMaster;
    private static DaoSession mDaoSession;

    public void init(Context context) {
        // 初始化数据库信息
        mDevOpenHelper = DbCopyHelper.getOpenHelper(context,pathStr,filename);
//        mDevOpenHelper = new MyOpenHelper(context,filename);
        getDaoMaster();
        getDaoSession();
    }

    public static DaoManager getInstance() {
        if (null == mDbManager) {
            synchronized (DaoManager.class) {
                if (null == mDbManager) {
                    mDbManager = new DaoManager();
                }
            }
        }
        return mDbManager;
    }

    /**
     * @desc 获取可读数据库
     * @autor Tiany
     * @time 2016/8/13
     **/
    private static SQLiteDatabase getReadableDatabase() {
        if (mDevOpenHelper == null) {
            throw new NullPointerException("Please excute init first.");
        }
        return mDevOpenHelper.getReadableDatabase();
    }

    /**
     * @desc 获取可写数据库
     * @autor Tiany
     * @time 2016/8/13
     **/
    private static SQLiteDatabase getWritableDatabase() {
        if (mDevOpenHelper == null) {
            throw new NullPointerException("Please excute init first.");
        }
        return mDevOpenHelper.getWritableDatabase();
    }

    /**
     * @desc 获取DaoMaster
     * @autor Tiany
     * @time 2016/8/13
     **/
    private DaoMaster getDaoMaster() {
        if (null == mDaoMaster) {
            synchronized (DaoManager.class) {
                if (null == mDaoMaster) {
                    mDaoMaster = new DaoMaster(getWritableDatabase());

                }
            }
        }
        return mDaoMaster;
    }

    /**
     * @desc 获取DaoSession
     * @autor Tiany
     * @time 2016/8/13
     **/
    private DaoSession getDaoSession() {
        if (null == mDaoSession) {
            synchronized (DaoManager.class) {
                mDaoSession = getDaoMaster().newSession();
            }
        }

        return mDaoSession;
    }

    public void clear() {
        getDaoSession().clear();
    }

    public List<AcupointBean> queryAcupoints(){
        List<AcupointBean> acupointBeanList = getDaoSession().getAcupointBeanDao().queryBuilder()
                .list();
        return acupointBeanList;
    }

    public AcupointBean queryAcupointByName(String name){
        AcupointBean acupointBean = getDaoSession().getAcupointBeanDao().queryBuilder().where(AcupointBeanDao.Properties.Acu_name.eq(name)).unique();
        return acupointBean;
    }

    public List<AcupointBean> queryAcupointLikeName(String name){
        List<AcupointBean> acupointBeans = getDaoSession().getAcupointBeanDao().queryBuilder().where(AcupointBeanDao.Properties.Acu_name.like(name)).list();
        return acupointBeans;
    }

    public AcupointParamBean queryAcupointParamByName(String name){
        AcupointParamBean acupointParamBean = getDaoSession().getAcupointParamBeanDao().queryBuilder().where(AcupointParamBeanDao.Properties.Name.eq(name)).unique();
        return acupointParamBean;
    }
    public void insertAcupointParam(AcupointParamBean bean) {
        getDaoSession().getAcupointParamBeanDao()
                .insertOrReplaceInTx(bean);
        clear();
    }

//    public void insertAdvTemplet(AdvTemplet advTemplet) {
//        getDaoSession().getAdvTempletDao()
//                .insertOrReplaceInTx(advTemplet);
//        clear();
//    }
//
//    public AdvTemplet queryAdvTemplet() {
//        List<AdvTemplet> advTempletlist = getDaoSession().getAdvTempletDao().queryBuilder().list();
//        AdvTemplet advTemplet = null;
//        if (advTempletlist != null && advTempletlist.size() > 0) {
//            advTemplet = advTempletlist.get(0);
//        }
//        return advTemplet;
//    }
//
//    public void deleteAllAdvTemplet() {
//        getDaoSession().getAdvTempletDao().deleteAll();
//        clear();
//    }
//
//    public void insertTempletItem(List<TempletItem> templetItems) {
//        getDaoSession().getTempletItemDao()
//                .insertOrReplaceInTx(templetItems);
//        clear();
//    }
//
//    public List<TempletItem> queryTempletItem() {
//        List<TempletItem> advTempletlist = getDaoSession().getTempletItemDao().queryBuilder()
//                .list();
//        return advTempletlist;
//    }
//
//    public void deleteAllTempletItem() {
//        getDaoSession().getTempletItemDao().deleteAll();
//        clear();
//    }
//
//    public void insertAdvSeatInfo(AdvSeatInfo advSeatInfo) {
//        getDaoSession().getAdvSeatInfoDao()
//                .insertOrReplace(advSeatInfo);
//        clear();
//    }
//
//    public void updateAdvSeatInfo(AdvSeatInfo advSeatInfo) {
//        getDaoSession().getAdvSeatInfoDao()
//                .update(advSeatInfo);
//        clear();
//    }
//
//    public List<AdvSeatInfo> queryAdvSeatInfos() {
//        List<AdvSeatInfo> advSeatInfos = getDaoSession()
//                .getAdvSeatInfoDao().queryBuilder().list();
//        return advSeatInfos;
//    }
//
//    public AdvSeatInfo queryAdvSeatInfo(String seatNo) {
//        AdvSeatInfo advSeatInfo = getDaoSession().getAdvSeatInfoDao().queryBuilder().where
//                (AdvSeatInfoDao.Properties.SeatNo.eq(seatNo)).unique();
//        return advSeatInfo;
//    }
//
//    public long countAdvSeatInfo(String seatNo) {
//        return getDaoSession().getAdvSeatInfoDao().queryBuilder()
//                .where(AdvSeatInfoDao.Properties.SeatNo.eq(seatNo)).count();
//    }
//
//    public void deleteAdvSeatInfos(String seatNo) {
//        getDaoSession().getAdvSeatInfoDao()
//                .queryBuilder()
//                .where(AdvSeatInfoDao.Properties.SeatNo.eq(seatNo))
//                .buildDelete().executeDeleteWithoutDetachingEntities();
//        clear();
//    }
//
//    public void deleteAllAdvSeatInfo() {
//        getDaoSession().getAdvSeatInfoDao().deleteAll();
//        clear();
//    }
//
//    public void insertAdvSeatPlayLog(AdvSeatPlayLog advSeatPlayLog) {
//        getDaoSession().getAdvSeatPlayLogDao()
//                .insertOrReplace(advSeatPlayLog);
//        clear();
//    }
//
//    public void updateAdvSeatPlayLog(AdvSeatPlayLog advSeatPlayLog) {
//        getDaoSession().getAdvSeatPlayLogDao()
//                .update(advSeatPlayLog);
//        clear();
//    }
//
//    public List<AdvSeatPlayLog> queryAdvSeatPlayLog() {
//        List<AdvSeatPlayLog> advSeatPlayLogs = getDaoSession().getAdvSeatPlayLogDao()
//                .queryBuilder().list();
//        return advSeatPlayLogs;
//    }
//
//    public void deleteAdvSeatPlayLog(String seatNo) {
//        getDaoSession().getAdvSeatPlayLogDao()
//                .queryBuilder()
//                .where(AdvSeatPlayLogDao.Properties.SeatNo.eq(seatNo))
//                .buildDelete().executeDeleteWithoutDetachingEntities();
//        clear();
//    }
//
//    public void deleteAllSeatPlayLog() {
//        getDaoSession().getAdvSeatPlayLogDao().deleteAll();
//        clear();
//    }
//
//    public List<PlayItem> queryPlayItems(int playItemId) {
//        return getDaoSession()
//                .getPlayItemDao().queryBuilder()
//                .where(PlayItemDao.Properties.PlayItemId.eq(playItemId))
//                .list();
//    }
//
//    public long countPlayItems(long resId) {
//        return getDaoSession()
//                .getPlayItemDao().queryBuilder().where(PlayItemDao.Properties.SourcesId.eq(resId)
//                ).count();
//    }
//
//    public long countPlayItemsByPlayItemId(long resId) {
//        return getDaoSession()
//                .getPlayItemDao().queryBuilder().where(PlayItemDao.Properties.PlayItemId.eq(resId)
//                ).count();
//    }
//
//    public long countPlayItems(int plkayItemId, long resId) {
//        return getDaoSession()
//                .getPlayItemDao().queryBuilder().where(PlayItemDao.Properties.SourcesId.eq(resId),
//                        PlayItemDao.Properties.PlayItemId.eq(plkayItemId)).count();
//    }
//
//    public void insertPlayItems(List<PlayItem> playItems) {
//        getDaoSession().getPlayItemDao().insertOrReplaceInTx(playItems);
//        clear();
//    }
//
//    public void insertPlayItem(PlayItem playItem) {
//        getDaoSession().getPlayItemDao().insertOrReplace(playItem);
//        clear();
//    }
//
//    public void updatePlayItems(List<PlayItem> playItems) {
//        getDaoSession().getPlayItemDao().updateInTx(playItems);
//        clear();
//    }
//
//    public void deletePlayItems(String seatNo) {
//        getDaoSession().getPlayItemDao().queryBuilder().where(PlayItemDao.Properties.SeatNo.eq
//                (seatNo)).buildDelete().executeDeleteWithoutDetachingEntities();
//        clear();
//    }
//
//    public void deletePlayItems(String seatNo, int playItemId) {
//        getDaoSession().getPlayItemDao().queryBuilder().where(PlayItemDao.Properties.SeatNo.eq
//                (seatNo), PlayItemDao.Properties.PlayItemId.eq
//                (playItemId)).buildDelete().executeDeleteWithoutDetachingEntities();
//        clear();
//    }
//
//    public void deleteAllPlayItem() {
//        getDaoSession().getPlayItemDao().deleteAll();
//        clear();
//    }
//
//    public void insertResourceItem(ResourceItem resourceItem) {
//        getDaoSession().getResourceItemDao().insertOrReplace(resourceItem);
//        clear();
//    }
//
//    public List<ResourceItem> queryResourceItems() {
//        return getDaoSession().getResourceItemDao()
//                .queryBuilder().list();
//    }
//
//    public long countResourceItems(Long resId) {
//        return getDaoSession().getResourceItemDao()
//                .queryBuilder().where(ResourceItemDao.Properties.ResourceId.eq(resId)).count();
//    }
//
//    public void deleteResourceItems(Long resId) {
//        getDaoSession().getResourceItemDao()
//                .queryBuilder()
//                .where(ResourceItemDao.Properties.ResourceId.eq(resId))
//                .buildDelete().executeDeleteWithoutDetachingEntities();
//        clear();
//    }
//
//    public void deleteAllResourceItem() {
//        getDaoSession().getResourceItemDao().deleteAll();
//        clear();
//    }
}
