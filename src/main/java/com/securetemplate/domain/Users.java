package com.securetemplate.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users", schema = "public")
public class Users implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "send_email", columnDefinition="boolean default false")
    private Boolean sendEmail;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "send_sms", columnDefinition="boolean default false")
    private Boolean sendSms;

    @Column(name = "viber_id")
    private String viberId;

    @Column(name = "send_viber", columnDefinition="boolean default false")
    private Boolean sendViber;

    @Column(name = "last_login")
    private Timestamp lastLogin;

    @Column(name = "send_notif_no_new_users")
    private Boolean sendNotifNoNewUsers;

    @Column(name = "send_notif_no_new_sessions")
    private Boolean sendNotifNoNewSessions;

    @Column(name = "send_notif_nginx")
    private Boolean sendNotifNginx;

    @Column(name = "send_notif_postgres")
    private Boolean sendNotifPostgres;

    @Column(name = "send_notif_radius")
    private Boolean sendNotifRadius;

    @Column(name = "send_notif_cp_app")
    private Boolean sendNotifCpApp;

    @Column(name = "send_notif_api_test")
    private Boolean sendNotifApiTest;

    @Column(name = "send_notif_cert_expires")
    private Boolean sendNotifCertExpires;

    @Column(name = "cert_expires_days")
    private Integer certExpiresDays;

    @Column(name = "send_notif_replication")
    private Boolean sendNotifReplication;

    @Column(name = "replication_warning_count")
    private Integer replicationWarningCount;

    @Column(name = "send_notif_avalilable_space")
    private Boolean sendNotifAvalilableSpace;

    @Column(name = "normal_free_space_percent")
    private Integer normalFreeSpacePercent;

    @Column(name = "critical_free_space_percent")
    private Integer criticalFreeSpacePercent;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @JsonIgnore
    @Transient
    List<GrantedAuthority> authorities = new ArrayList<>();

    public void setAuthorities() {
        this.authorities = (List<GrantedAuthority>) getAuthorities();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        System.out.println(grantedAuthorities);
        return grantedAuthorities;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getSendEmail() {
        return sendEmail;
    }

    public void setSendEmail(Boolean sendEmail) {
        this.sendEmail = sendEmail;
    }

    public Boolean getSendSms() {
        return sendSms;
    }

    public void setSendSms(Boolean sendSms) {
        this.sendSms = sendSms;
    }

    public Timestamp getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Timestamp lastLogin) {
        this.lastLogin = lastLogin;
    }

    public Boolean getSendNotifNoNewUsers() {
        return sendNotifNoNewUsers;
    }

    public void setSendNotifNoNewUsers(Boolean sendNotifNoNewUsers) {
        this.sendNotifNoNewUsers = sendNotifNoNewUsers;
    }

    public Boolean getSendNotifNoNewSessions() {
        return sendNotifNoNewSessions;
    }

    public void setSendNotifNoNewSessions(Boolean sendNotifNoNewSessions) {
        this.sendNotifNoNewSessions = sendNotifNoNewSessions;
    }

    public Boolean getSendNotifNginx() {
        return sendNotifNginx;
    }

    public void setSendNotifNginx(Boolean sendNotifNginx) {
        this.sendNotifNginx = sendNotifNginx;
    }

    public Boolean getSendNotifPostgres() {
        return sendNotifPostgres;
    }

    public void setSendNotifPostgres(Boolean sendNotifPostgres) {
        this.sendNotifPostgres = sendNotifPostgres;
    }

    public Boolean getSendNotifRadius() {
        return sendNotifRadius;
    }

    public void setSendNotifRadius(Boolean sendNotifRadius) {
        this.sendNotifRadius = sendNotifRadius;
    }

    public Boolean getSendNotifCpApp() {
        return sendNotifCpApp;
    }

    public void setSendNotifCpApp(Boolean sendNotifCpApp) {
        this.sendNotifCpApp = sendNotifCpApp;
    }

    public Boolean getSendNotifApiTest() {
        return sendNotifApiTest;
    }

    public void setSendNotifApiTest(Boolean sendNotifApiTest) {
        this.sendNotifApiTest = sendNotifApiTest;
    }

    public Boolean getSendNotifCertExpires() {
        return sendNotifCertExpires;
    }

    public void setSendNotifCertExpires(Boolean sendNotifCertExpires) {
        this.sendNotifCertExpires = sendNotifCertExpires;
    }

    public Integer getCertExpiresDays() {
        return certExpiresDays;
    }

    public void setCertExpiresDays(Integer certExpiresDays) {
        this.certExpiresDays = certExpiresDays;
    }

    public Boolean getSendNotifReplication() {
        return sendNotifReplication;
    }

    public void setSendNotifReplication(Boolean sendNotifReplication) {
        this.sendNotifReplication = sendNotifReplication;
    }

    public Integer getReplicationWarningCount() {
        return replicationWarningCount;
    }

    public void setReplicationWarningCount(Integer replicationWarningCount) {
        this.replicationWarningCount = replicationWarningCount;
    }

    public Boolean getSendNotifAvalilableSpace() {
        return sendNotifAvalilableSpace;
    }

    public void setSendNotifAvalilableSpace(Boolean sendNotifAvalilableSpace) {
        this.sendNotifAvalilableSpace = sendNotifAvalilableSpace;
    }

    public Integer getNormalFreeSpacePercent() {
        return normalFreeSpacePercent;
    }

    public void setNormalFreeSpacePercent(Integer normalFreeSpacePercent) {
        this.normalFreeSpacePercent = normalFreeSpacePercent;
    }

    public Integer getCriticalFreeSpacePercent() {
        return criticalFreeSpacePercent;
    }

    public void setCriticalFreeSpacePercent(Integer criticalFreeSpacePercent) {
        this.criticalFreeSpacePercent = criticalFreeSpacePercent;
    }

    public String getViberId() {
        return viberId;
    }

    public void setViberId(String viberId) {
        this.viberId = viberId;
    }

    public Boolean getSendViber() {
        return sendViber;
    }

    public void setSendViber(Boolean sendViber) {
        this.sendViber = sendViber;
    }

}
