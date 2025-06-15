package com.iti.textcom.entity;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "sipfriends")
public class SipFriend implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", length = 40, unique = true, nullable = false)
    private String name;

    @Column(name = "ipaddr", length = 40)
    private String ipaddr;

    @Column(name = "port")
    private Integer port;

    @Column(name = "regseconds")
    private Integer regseconds;

    @Column(name = "defaultuser", length = 40)
    private String defaultuser;

    @Column(name = "fullcontact", length = 80)
    private String fullcontact;

    @Column(name = "regserver", length = 20)
    private String regserver;

    @Column(name = "useragent", length = 255)
    private String useragent;

    @Column(name = "lastms")
    private Integer lastms;

    @Column(name = "host", length = 40)
    private String host;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", length = 10)
    private TypeEnum type;

    @Column(name = "context", length = 40)
    private String context;

    @Column(name = "permit", length = 95)
    private String permit;

    @Column(name = "deny", length = 95)
    private String deny;

    @Column(name = "secret", length = 40)
    private String secret;

    @Column(name = "md5secret", length = 40)
    private String md5secret;

    @Column(name = "remotesecret", length = 40)
    private String remotesecret;

    @Column(name = "transport", length = 40)
    private String transport;

    @Column(name = "dtmfmode", length = 40)
    private String dtmfmode;

    @Column(name = "directmedia", length = 40)
    private String directmedia;

    @Column(name = "nat", length = 40)
    private String nat;

    @Column(name = "callgroup", length = 40)
    private String callgroup;

    @Column(name = "pickupgroup", length = 40)
    private String pickupgroup;

    @Column(name = "language", length = 40)
    private String language;

    @Column(name = "disallow", length = 40)
    private String disallow;

    @Column(name = "allow", length = 40)
    private String allow;

    @Column(name = "insecure", length = 40)
    private String insecure;

    @Enumerated(EnumType.STRING)
    @Column(name = "trustrpid", length = 3)
    private YesNoEnum trustrpid;

    @Enumerated(EnumType.STRING)
    @Column(name = "progressinband", length = 6)
    private ProgressInbandEnum progressinband;

    @Enumerated(EnumType.STRING)
    @Column(name = "promiscredir", length = 3)
    private YesNoEnum promiscredir;

    @Enumerated(EnumType.STRING)
    @Column(name = "useclientcode", length = 3)
    private YesNoEnum useclientcode;

    @Column(name = "accountcode", length = 40)
    private String accountcode;

    @Column(name = "setvar", length = 40)
    private String setvar;

    @Column(name = "callerid", length = 40)
    private String callerid;

    @Column(name = "amaflags", length = 40)
    private String amaflags;

    @Enumerated(EnumType.STRING)
    @Column(name = "callcounter", length = 3)
    private YesNoEnum callcounter;

    @Column(name = "busylevel")
    private Integer busylevel;

    @Enumerated(EnumType.STRING)
    @Column(name = "allowoverlap", length = 3)
    private YesNoEnum allowoverlap;

    @Enumerated(EnumType.STRING)
    @Column(name = "allowsubscribe", length = 3)
    private YesNoEnum allowsubscribe;

    @Enumerated(EnumType.STRING)
    @Column(name = "videosupport", length = 3)
    private YesNoEnum videosupport;

    @Column(name = "maxcallbitrate")
    private Integer maxcallbitrate;

    @Enumerated(EnumType.STRING)
    @Column(name = "rfc2833compensate", length = 3)
    private YesNoEnum rfc2833compensate;

    @Column(name = "mailbox", length = 40)
    private String mailbox;

    @Enumerated(EnumType.STRING)
    @Column(name = "session_timers", length = 9)
    private SessionTimersEnum session_timers;

    @Column(name = "session_expires")
    private Integer session_expires;

    @Column(name = "session_minse")
    private Integer session_minse;

    @Enumerated(EnumType.STRING)
    @Column(name = "session_refresher", length = 3)
    private YesNoEnum session_refresher;

    @Column(name = "t38pt_usertpsource", length = 40)
    private String t38pt_usertpsource;

    @Column(name = "regexten", length = 40)
    private String regexten;

    @Column(name = "fromdomain", length = 40)
    private String fromdomain;

    @Column(name = "fromuser", length = 40)
    private String fromuser;

    @Column(name = "`qualify`", length = 40)
    private String qualify;

    @Column(name = "defaultip", length = 40)
    private String defaultip;

    @Column(name = "rtptimeout")
    private Integer rtptimeout;

    @Column(name = "rtpholdtimeout")
    private Integer rtpholdtimeout;

    @Enumerated(EnumType.STRING)
    @Column(name = "sendrpid", length = 3)
    private YesNoEnum sendrpid;

    @Column(name = "outboundproxy", length = 40)
    private String outboundproxy;

    @Column(name = "callbackextension", length = 40)
    private String callbackextension;

    @Column(name = "timert1")
    private Integer timert1;

    @Column(name = "timerb")
    private Integer timerb;

    @Column(name = "qualifyfreq")
    private Integer qualifyfreq;

    @Enumerated(EnumType.STRING)
    @Column(name = "constantssrc", length = 3)
    private YesNoEnum constantssrc;

    @Column(name = "contactpermit", length = 95)
    private String contactpermit;

    @Column(name = "contactdeny", length = 95)
    private String contactdeny;

    @Enumerated(EnumType.STRING)
    @Column(name = "usereqphone", length = 3)
    private YesNoEnum usereqphone;

    @Enumerated(EnumType.STRING)
    @Column(name = "textsupport", length = 3)
    private YesNoEnum textsupport;

    @Enumerated(EnumType.STRING)
    @Column(name = "faxdetect", length = 3)
    private YesNoEnum faxdetect;

    @Enumerated(EnumType.STRING)
    @Column(name = "buggymwi", length = 3)
    private YesNoEnum buggymwi;

    @Column(name = "auth", length = 40)
    private String auth;

    @Column(name = "fullname", length = 40)
    private String fullname;

    @Column(name = "trunkname", length = 40)
    private String trunkname;

    @Column(name = "cid_number", length = 40)
    private String cid_number;

    @Enumerated(EnumType.STRING)
    @Column(name = "callingpres", length = 20)
    private CallingPresEnum callingpres;

    @Column(name = "mohinterpret", length = 40)
    private String mohinterpret;

    @Column(name = "mohsuggest", length = 40)
    private String mohsuggest;

    @Column(name = "parkinglot", length = 40)
    private String parkinglot;

    @Enumerated(EnumType.STRING)
    @Column(name = "hasvoicemail", length = 3)
    private YesNoEnum hasvoicemail;

    @Enumerated(EnumType.STRING)
    @Column(name = "subscribemwi", length = 3)
    private YesNoEnum subscribemwi;

    @Column(name = "vmexten", length = 40)
    private String vmexten;

    @Enumerated(EnumType.STRING)
    @Column(name = "autoframing", length = 3)
    private YesNoEnum autoframing;

    @Column(name = "rtpkeepalive")
    private Integer rtpkeepalive;

    @Column(name = "call_limit")
    private Integer call_limit;

    @Enumerated(EnumType.STRING)
    @Column(name = "g726nonstandard", length = 3)
    private YesNoEnum g726nonstandard;

    @Enumerated(EnumType.STRING)
    @Column(name = "ignoresdpversion", length = 3)
    private YesNoEnum ignoresdpversion;

    @Enumerated(EnumType.STRING)
    @Column(name = "allowtransfer", length = 3)
    private YesNoEnum allowtransfer;

    @Enumerated(EnumType.STRING)
    @Column(name = "dynamic", length = 3)
    private YesNoEnum dynamic;

    @Column(name = "path", length = 256)
    private String path;

    @Enumerated(EnumType.STRING)
    @Column(name = "supportpath", length = 3)
    private YesNoEnum supportpath;

    public SipFriend() {
    }

    // Minimal constructor for registration
    public SipFriend(String name, String defaultuser, String host, TypeEnum type, String context, String md5secret, String dtmfmode, String nat, String allow, YesNoEnum dynamic) {
        this.name = name;
        this.defaultuser = defaultuser;
        this.host = host;
        this.type = type;
        this.context = context;
        this.md5secret = md5secret;
        this.dtmfmode = dtmfmode;
        this.nat = nat;
        this.allow = allow;
        this.dynamic = dynamic;
    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIpaddr() {
        return ipaddr;
    }

    public void setIpaddr(String ipaddr) {
        this.ipaddr = ipaddr;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public Integer getRegseconds() {
        return regseconds;
    }

    public void setRegseconds(Integer regseconds) {
        this.regseconds = regseconds;
    }

    public String getDefaultuser() {
        return defaultuser;
    }

    public void setDefaultuser(String defaultuser) {
        this.defaultuser = defaultuser;
    }

    public String getFullcontact() {
        return fullcontact;
    }

    public void setFullcontact(String fullcontact) {
        this.fullcontact = fullcontact;
    }

    public String getRegserver() {
        return regserver;
    }

    public void setRegserver(String regserver) {
        this.regserver = regserver;
    }

    public String getUseragent() {
        return useragent;
    }

    public void setUseragent(String useragent) {
        this.useragent = useragent;
    }

    public Integer getLastms() {
        return lastms;
    }

    public void setLastms(Integer lastms) {
        this.lastms = lastms;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public TypeEnum getType() {
        return type;
    }

    public void setType(TypeEnum type) {
        this.type = type;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getPermit() {
        return permit;
    }

    public void setPermit(String permit) {
        this.permit = permit;
    }

    public String getDeny() {
        return deny;
    }

    public void setDeny(String deny) {
        this.deny = deny;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getMd5secret() {
        return md5secret;
    }

    public void setMd5secret(String md5secret) {
        this.md5secret = md5secret;
    }

    public String getRemotesecret() {
        return remotesecret;
    }

    public void setRemotesecret(String remotesecret) {
        this.remotesecret = remotesecret;
    }

    public String getTransport() {
        return transport;
    }

    public void setTransport(String transport) {
        this.transport = transport;
    }

    public String getDtmfmode() {
        return dtmfmode;
    }

    public void setDtmfmode(String dtmfmode) {
        this.dtmfmode = dtmfmode;
    }

    public String getDirectmedia() {
        return directmedia;
    }

    public void setDirectmedia(String directmedia) {
        this.directmedia = directmedia;
    }

    public String getNat() {
        return nat;
    }

    public void setNat(String nat) {
        this.nat = nat;
    }

    public String getCallgroup() {
        return callgroup;
    }

    public void setCallgroup(String callgroup) {
        this.callgroup = callgroup;
    }

    public String getPickupgroup() {
        return pickupgroup;
    }

    public void setPickupgroup(String pickupgroup) {
        this.pickupgroup = pickupgroup;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getDisallow() {
        return disallow;
    }

    public void setDisallow(String disallow) {
        this.disallow = disallow;
    }

    public String getAllow() {
        return allow;
    }

    public void setAllow(String allow) {
        this.allow = allow;
    }

    public String getInsecure() {
        return insecure;
    }

    public void setInsecure(String insecure) {
        this.insecure = insecure;
    }

    public YesNoEnum getTrustrpid() {
        return trustrpid;
    }

    public void setTrustrpid(YesNoEnum trustrpid) {
        this.trustrpid = trustrpid;
    }

    public ProgressInbandEnum getProgressinband() {
        return progressinband;
    }

    public void setProgressinband(ProgressInbandEnum progressinband) {
        this.progressinband = progressinband;
    }

    public YesNoEnum getPromiscredir() {
        return promiscredir;
    }

    public void setPromiscredir(YesNoEnum promiscredir) {
        this.promiscredir = promiscredir;
    }

    public YesNoEnum getUseclientcode() {
        return useclientcode;
    }

    public void setUseclientcode(YesNoEnum useclientcode) {
        this.useclientcode = useclientcode;
    }

    public String getAccountcode() {
        return accountcode;
    }

    public void setAccountcode(String accountcode) {
        this.accountcode = accountcode;
    }

    public String getSetvar() {
        return setvar;
    }

    public void setSetvar(String setvar) {
        this.setvar = setvar;
    }

    public String getCallerid() {
        return callerid;
    }

    public void setCallerid(String callerid) {
        this.callerid = callerid;
    }

    public String getAmaflags() {
        return amaflags;
    }

    public void setAmaflags(String amaflags) {
        this.amaflags = amaflags;
    }

    public YesNoEnum getCallcounter() {
        return callcounter;
    }

    public void setCallcounter(YesNoEnum callcounter) {
        this.callcounter = callcounter;
    }

    public Integer getBusylevel() {
        return busylevel;
    }

    public void setBusylevel(Integer busylevel) {
        this.busylevel = busylevel;
    }

    public YesNoEnum getAllowoverlap() {
        return allowoverlap;
    }

    public void setAllowoverlap(YesNoEnum allowoverlap) {
        this.allowoverlap = allowoverlap;
    }

    public YesNoEnum getAllowsubscribe() {
        return allowsubscribe;
    }

    public void setAllowsubscribe(YesNoEnum allowsubscribe) {
        this.allowsubscribe = allowsubscribe;
    }

    public YesNoEnum getVideosupport() {
        return videosupport;
    }

    public void setVideosupport(YesNoEnum videosupport) {
        this.videosupport = videosupport;
    }

    public Integer getMaxcallbitrate() {
        return maxcallbitrate;
    }

    public void setMaxcallbitrate(Integer maxcallbitrate) {
        this.maxcallbitrate = maxcallbitrate;
    }

    public YesNoEnum getRfc2833compensate() {
        return rfc2833compensate;
    }

    public void setRfc2833compensate(YesNoEnum rfc2833compensate) {
        this.rfc2833compensate = rfc2833compensate;
    }

    public String getMailbox() {
        return mailbox;
    }

    public void setMailbox(String mailbox) {
        this.mailbox = mailbox;
    }

    public SessionTimersEnum getSession_timers() {
        return session_timers;
    }

    public void setSession_timers(SessionTimersEnum session_timers) {
        this.session_timers = session_timers;
    }

    public Integer getSession_expires() {
        return session_expires;
    }

    public void setSession_expires(Integer session_expires) {
        this.session_expires = session_expires;
    }

    public Integer getSession_minse() {
        return session_minse;
    }

    public void setSession_minse(Integer session_minse) {
        this.session_minse = session_minse;
    }

    public YesNoEnum getSession_refresher() {
        return session_refresher;
    }

    public void setSession_refresher(YesNoEnum session_refresher) {
        this.session_refresher = session_refresher;
    }

    public String getT38pt_usertpsource() {
        return t38pt_usertpsource;
    }

    public void setT38pt_usertpsource(String t38pt_usertpsource) {
        this.t38pt_usertpsource = t38pt_usertpsource;
    }

    public String getRegexten() {
        return regexten;
    }

    public void setRegexten(String regexten) {
        this.regexten = regexten;
    }

    public String getFromdomain() {
        return fromdomain;
    }

    public void setFromdomain(String fromdomain) {
        this.fromdomain = fromdomain;
    }

    public String getFromuser() {
        return fromuser;
    }

    public void setFromuser(String fromuser) {
        this.fromuser = fromuser;
    }

    public String getQualify() {
        return qualify;
    }

    public void setQualify(String qualify) {
        this.qualify = qualify;
    }

    public String getDefaultip() {
        return defaultip;
    }

    public void setDefaultip(String defaultip) {
        this.defaultip = defaultip;
    }

    public Integer getRtptimeout() {
        return rtptimeout;
    }

    public void setRtptimeout(Integer rtptimeout) {
        this.rtptimeout = rtptimeout;
    }

    public Integer getRtpholdtimeout() {
        return rtpholdtimeout;
    }

    public void setRtpholdtimeout(Integer rtpholdtimeout) {
        this.rtpholdtimeout = rtpholdtimeout;
    }

    public YesNoEnum getSendrpid() {
        return sendrpid;
    }

    public void setSendrpid(YesNoEnum sendrpid) {
        this.sendrpid = sendrpid;
    }

    public String getOutboundproxy() {
        return outboundproxy;
    }

    public void setOutboundproxy(String outboundproxy) {
        this.outboundproxy = outboundproxy;
    }

    public String getCallbackextension() {
        return callbackextension;
    }

    public void setCallbackextension(String callbackextension) {
        this.callbackextension = callbackextension;
    }

    public Integer getTimert1() {
        return timert1;
    }

    public void setTimert1(Integer timert1) {
        this.timert1 = timert1;
    }

    public Integer getTimerb() {
        return timerb;
    }

    public void setTimerb(Integer timerb) {
        this.timerb = timerb;
    }

    public Integer getQualifyfreq() {
        return qualifyfreq;
    }

    public void setQualifyfreq(Integer qualifyfreq) {
        this.qualifyfreq = qualifyfreq;
    }

    public YesNoEnum getConstantssrc() {
        return constantssrc;
    }

    public void setConstantssrc(YesNoEnum constantssrc) {
        this.constantssrc = constantssrc;
    }

    public String getContactpermit() {
        return contactpermit;
    }

    public void setContactpermit(String contactpermit) {
        this.contactpermit = contactpermit;
    }

    public String getContactdeny() {
        return contactdeny;
    }

    public void setContactdeny(String contactdeny) {
        this.contactdeny = contactdeny;
    }

    public YesNoEnum getUsereqphone() {
        return usereqphone;
    }

    public void setUsereqphone(YesNoEnum usereqphone) {
        this.usereqphone = usereqphone;
    }

    public YesNoEnum getTextsupport() {
        return textsupport;
    }

    public void setTextsupport(YesNoEnum textsupport) {
        this.textsupport = textsupport;
    }

    public YesNoEnum getFaxdetect() {
        return faxdetect;
    }

    public void setFaxdetect(YesNoEnum faxdetect) {
        this.faxdetect = faxdetect;
    }

    public YesNoEnum getBuggymwi() {
        return buggymwi;
    }

    public void setBuggymwi(YesNoEnum buggymwi) {
        this.buggymwi = buggymwi;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getTrunkname() {
        return trunkname;
    }

    public void setTrunkname(String trunkname) {
        this.trunkname = trunkname;
    }

    public String getCid_number() {
        return cid_number;
    }

    public void setCid_number(String cid_number) {
        this.cid_number = cid_number;
    }

    public CallingPresEnum getCallingpres() {
        return callingpres;
    }

    public void setCallingpres(CallingPresEnum callingpres) {
        this.callingpres = callingpres;
    }

    public String getMohinterpret() {
        return mohinterpret;
    }

    public void setMohinterpret(String mohinterpret) {
        this.mohinterpret = mohinterpret;
    }

    public String getMohsuggest() {
        return mohsuggest;
    }

    public void setMohsuggest(String mohsuggest) {
        this.mohsuggest = mohsuggest;
    }

    public String getParkinglot() {
        return parkinglot;
    }

    public void setParkinglot(String parkinglot) {
        this.parkinglot = parkinglot;
    }

    public YesNoEnum getHasvoicemail() {
        return hasvoicemail;
    }

    public void setHasvoicemail(YesNoEnum hasvoicemail) {
        this.hasvoicemail = hasvoicemail;
    }

    public YesNoEnum getSubscribemwi() {
        return subscribemwi;
    }

    public void setSubscribemwi(YesNoEnum subscribemwi) {
        this.subscribemwi = subscribemwi;
    }

    public String getVmexten() {
        return vmexten;
    }

    public void setVmexten(String vmexten) {
        this.vmexten = vmexten;
    }

    public YesNoEnum getAutoframing() {
        return autoframing;
    }

    public void setAutoframing(YesNoEnum autoframing) {
        this.autoframing = autoframing;
    }

    public Integer getRtpkeepalive() {
        return rtpkeepalive;
    }

    public void setRtpkeepalive(Integer rtpkeepalive) {
        this.rtpkeepalive = rtpkeepalive;
    }

    public Integer getCall_limit() {
        return call_limit;
    }

    public void setCall_limit(Integer call_limit) {
        this.call_limit = call_limit;
    }

    public YesNoEnum getG726nonstandard() {
        return g726nonstandard;
    }

    public void setG726nonstandard(YesNoEnum g726nonstandard) {
        this.g726nonstandard = g726nonstandard;
    }

    public YesNoEnum getIgnoresdpversion() {
        return ignoresdpversion;
    }

    public void setIgnoresdpversion(YesNoEnum ignoresdpversion) {
        this.ignoresdpversion = ignoresdpversion;
    }

    public YesNoEnum getAllowtransfer() {
        return allowtransfer;
    }

    public void setAllowtransfer(YesNoEnum allowtransfer) {
        this.allowtransfer = allowtransfer;
    }

    public YesNoEnum getDynamic() {
        return dynamic;
    }

    public void setDynamic(YesNoEnum dynamic) {
        this.dynamic = dynamic;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public YesNoEnum getSupportpath() {
        return supportpath;
    }

    public void setSupportpath(YesNoEnum supportpath) {
        this.supportpath = supportpath;
    }
} 