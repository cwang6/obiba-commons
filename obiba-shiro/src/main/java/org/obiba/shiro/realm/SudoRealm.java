/*
 * Copyright (c) 2016 OBiBa. All rights reserved.
 *
 * This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.obiba.shiro.realm;

import java.io.Serializable;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAccount;
import org.apache.shiro.authc.credential.AllowAllCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.obiba.shiro.authc.SudoAuthToken;
import org.springframework.stereotype.Component;

@Component
public class SudoRealm extends AuthorizingRealm {

  private final AuthenticationInfo simpleAccount = new SimpleAccount(SudoPrincipal.INSTANCE, null, getName());

  public SudoRealm() {
    setCredentialsMatcher(new AllowAllCredentialsMatcher());
  }

  @Override
  public boolean supports(AuthenticationToken token) {
    return token instanceof SudoAuthToken;
  }

  @Override
  protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
    // SudoAuthToken sudoToken = (SudoAuthToken) token;
    // TODO: test some kind of permission to conditionally accept the sudo request:
    // SecurityUtils.getSecurityManager().isPermitted(sudoToken.getSudoer(), "sudo")
    return simpleAccount;
  }

  @Override
  protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
    SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
    if(principals.oneByType(SudoPrincipal.class) != null) {
      info.addStringPermission("*");
    }
    return info;
  }

  @SuppressWarnings({ "ClassMayBeInterface", "EmptyClass", "Singleton" })
  public static class SudoPrincipal implements Serializable {
    private static final long serialVersionUID = -5315801516710903139L;

    public static final SudoPrincipal INSTANCE = new SudoPrincipal();

    private SudoPrincipal() { }
  }
}
