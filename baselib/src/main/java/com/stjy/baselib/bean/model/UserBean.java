package com.stjy.baselib.bean.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * @author daifalin
 * @date 2019/2/19 11:19 AM
 * @ClassName UserBean
 * @Description
 */
public class UserBean implements Parcelable {
    @SerializedName("id")
    private String id;
    @SerializedName("areaId")
    private int areaId;
    @SerializedName("organId")
    private int organId;
    @SerializedName("organName")
    private String organName;
    @SerializedName("userType")
    private String userType;
    @SerializedName("account")
    private String account;
    @SerializedName("name")
    private String name;
    @SerializedName("nickname")
    private String nickName;
    @SerializedName("postId")
    private int postId;
    @SerializedName("nation")
    private String nation;
    @SerializedName("sex")
    private int sex;
    @SerializedName("birthday")
    private long birthday;
    @SerializedName("status")
    private int status;
    @SerializedName("pictureUrl")
    private String pictureUrl;
    @SerializedName("introduction")
    private String introduction;
    @SerializedName("mobile")
    private String mobile;
    @SerializedName("qq")
    private String qq;
    @SerializedName("email")
    private String email;
    @SerializedName("idcard")
    private String idcard;
    @SerializedName("idcardAddress")
    private String idcardAddress;
    @SerializedName("address")
    private String address;
    @SerializedName("authenticated")
    private boolean authenticated;
    @SerializedName("idcardFrontPhotoUrl")
    private String idcardFrontPhotoUrl;
    @SerializedName("idcardBackPhotoUrl")
    private String idcardBackPhotoUrl;
    @SerializedName("facePhotoUrl")
    private String facePhotoUrl;
    @SerializedName("currentFacePhotoUrl")
    private String currentFacePhotoUrl;
    @SerializedName("roleIds")
    private String roleIds;
    @SerializedName("roleNames")
    private String roleNames;
    @SerializedName("endTime")
    private String endTime;
    @SerializedName("safetyEngineerCertificate")
    private boolean safetyEngineerCertificate;
    @SerializedName("safetyEngineerCertificateNo")
    private String safetyEngineerCertificateNo;
    @SerializedName("qualificationCertificate")
    private String qualificationCertificate;
    @SerializedName("createTime")
    private long createTime;
    @SerializedName("appealAuditStatus")
    private String appealAuditStatus;
    @SerializedName("certificationPhoto")
    private String certificationPhoto;
    @SerializedName("userMenus")
    private String userMenus;
    @SerializedName("superviseOrganParam")
    private String superviseOrganParam;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getAreaId() {
        return areaId;
    }

    public void setAreaId(int areaId) {
        this.areaId = areaId;
    }

    public int getOrganId() {
        return organId;
    }

    public void setOrganId(int organId) {
        this.organId = organId;
    }

    public String getOrganName() {
        return organName;
    }

    public void setOrganName(String organName) {
        this.organName = organName;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public long getBirthday() {
        return birthday;
    }

    public void setBirthday(long birthday) {
        this.birthday = birthday;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getPictureUrl() {
        //如果头像为null,默认为空字符
        return pictureUrl == null ? "" : pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public String getIdcardAddress() {
        return idcardAddress;
    }

    public void setIdcardAddress(String idcardAddress) {
        this.idcardAddress = idcardAddress;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isAuthenticated() {
        return authenticated;
    }

    public void setAuthenticated(boolean authenticated) {
        this.authenticated = authenticated;
    }

    public String getIdcardFrontPhotoUrl() {
        return idcardFrontPhotoUrl;
    }

    public void setIdcardFrontPhotoUrl(String idcardFrontPhotoUrl) {
        this.idcardFrontPhotoUrl = idcardFrontPhotoUrl;
    }

    public String getIdcardBackPhotoUrl() {
        return idcardBackPhotoUrl;
    }

    public void setIdcardBackPhotoUrl(String idcardBackPhotoUrl) {
        this.idcardBackPhotoUrl = idcardBackPhotoUrl;
    }

    public String getFacePhotoUrl() {
        return facePhotoUrl;
    }

    public void setFacePhotoUrl(String facePhotoUrl) {
        this.facePhotoUrl = facePhotoUrl;
    }

    public String getCurrentFacePhotoUrl() {
        return currentFacePhotoUrl;
    }

    public void setCurrentFacePhotoUrl(String currentFacePhotoUrl) {
        this.currentFacePhotoUrl = currentFacePhotoUrl;
    }

    public String getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(String roleIds) {
        this.roleIds = roleIds;
    }

    public String getRoleNames() {
        return roleNames;
    }

    public void setRoleNames(String roleNames) {
        this.roleNames = roleNames;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public boolean isSafetyEngineerCertificate() {
        return safetyEngineerCertificate;
    }

    public void setSafetyEngineerCertificate(boolean safetyEngineerCertificate) {
        this.safetyEngineerCertificate = safetyEngineerCertificate;
    }

    public String getSafetyEngineerCertificateNo() {
        return safetyEngineerCertificateNo;
    }

    public void setSafetyEngineerCertificateNo(String safetyEngineerCertificateNo) {
        this.safetyEngineerCertificateNo = safetyEngineerCertificateNo;
    }

    public String getQualificationCertificate() {
        return qualificationCertificate;
    }

    public void setQualificationCertificate(String qualificationCertificate) {
        this.qualificationCertificate = qualificationCertificate;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getAppealAuditStatus() {
        return appealAuditStatus;
    }

    public void setAppealAuditStatus(String appealAuditStatus) {
        this.appealAuditStatus = appealAuditStatus;
    }

    public String getCertificationPhoto() {
        return certificationPhoto;
    }

    public void setCertificationPhoto(String certificationPhoto) {
        this.certificationPhoto = certificationPhoto;
    }

    public String getUserMenus() {
        return userMenus;
    }

    public void setUserMenus(String userMenus) {
        this.userMenus = userMenus;
    }

    public String getSuperviseOrganParam() {
        return superviseOrganParam;
    }

    public void setSuperviseOrganParam(String superviseOrganParam) {
        this.superviseOrganParam = superviseOrganParam;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeInt(this.areaId);
        dest.writeInt(this.organId);
        dest.writeString(this.organName);
        dest.writeString(this.userType);
        dest.writeString(this.account);
        dest.writeString(this.name);
        dest.writeString(this.nickName);
        dest.writeInt(this.postId);
        dest.writeString(this.nation);
        dest.writeInt(this.sex);
        dest.writeLong(this.birthday);
        dest.writeInt(this.status);
        dest.writeString(this.pictureUrl);
        dest.writeString(this.introduction);
        dest.writeString(this.mobile);
        dest.writeString(this.qq);
        dest.writeString(this.email);
        dest.writeString(this.idcard);
        dest.writeString(this.idcardAddress);
        dest.writeString(this.address);
        dest.writeByte(this.authenticated ? (byte) 1 : (byte) 0);
        dest.writeString(this.idcardFrontPhotoUrl);
        dest.writeString(this.idcardBackPhotoUrl);
        dest.writeString(this.facePhotoUrl);
        dest.writeString(this.currentFacePhotoUrl);
        dest.writeString(this.roleIds);
        dest.writeString(this.roleNames);
        dest.writeString(this.endTime);
        dest.writeByte(this.safetyEngineerCertificate ? (byte) 1 : (byte) 0);
        dest.writeString(this.safetyEngineerCertificateNo);
        dest.writeString(this.qualificationCertificate);
        dest.writeLong(this.createTime);
        dest.writeString(this.appealAuditStatus);
        dest.writeString(this.certificationPhoto);
        dest.writeString(this.userMenus);
        dest.writeString(this.superviseOrganParam);
    }

    public UserBean() {
    }

    protected UserBean(Parcel in) {
        this.id = in.readString();
        this.areaId = in.readInt();
        this.organId = in.readInt();
        this.organName = in.readString();
        this.userType = in.readString();
        this.account = in.readString();
        this.name = in.readString();
        this.nickName = in.readString();
        this.postId = in.readInt();
        this.nation = in.readString();
        this.sex = in.readInt();
        this.birthday = in.readLong();
        this.status = in.readInt();
        this.pictureUrl = in.readString();
        this.introduction = in.readString();
        this.mobile = in.readString();
        this.qq = in.readString();
        this.email = in.readString();
        this.idcard = in.readString();
        this.idcardAddress = in.readString();
        this.address = in.readString();
        this.authenticated = in.readByte() != 0;
        this.idcardFrontPhotoUrl = in.readString();
        this.idcardBackPhotoUrl = in.readString();
        this.facePhotoUrl = in.readString();
        this.currentFacePhotoUrl = in.readString();
        this.roleIds = in.readString();
        this.roleNames = in.readString();
        this.endTime = in.readString();
        this.safetyEngineerCertificate = in.readByte() != 0;
        this.safetyEngineerCertificateNo = in.readString();
        this.qualificationCertificate = in.readString();
        this.createTime = in.readLong();
        this.appealAuditStatus = in.readString();
        this.certificationPhoto = in.readString();
        this.userMenus = in.readString();
        this.superviseOrganParam = in.readString();
    }

    public static final Creator<UserBean> CREATOR = new Creator<UserBean>() {
        @Override
        public UserBean createFromParcel(Parcel source) {
            return new UserBean(source);
        }

        @Override
        public UserBean[] newArray(int size) {
            return new UserBean[size];
        }
    };
}
