package invengo.cn.pay.service;

import invengo.cn.pay.model.DreamResponse;
import invengo.cn.pay.model.access.MemberAccess;

public interface IAccessMemberService
{
    public DreamResponse setMember(MemberAccess dto);
}
