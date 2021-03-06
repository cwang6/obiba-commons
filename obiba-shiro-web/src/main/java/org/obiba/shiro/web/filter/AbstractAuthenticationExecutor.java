/*
 * Copyright (c) 2016 OBiBa. All rights reserved.
 *
 * This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.obiba.shiro.web.filter;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ThreadContext;

public abstract class AbstractAuthenticationExecutor implements AuthenticationExecutor {

  @Override
  public Subject login(AuthenticationToken token) throws AuthenticationException {
    return login(token, null);
  }

  @Override
  public Subject login(AuthenticationToken token, String sessionId) throws AuthenticationException {
    Subject subject = sessionId == null
        ? SecurityUtils.getSubject()
        : new Subject.Builder(SecurityUtils.getSecurityManager()).sessionId(sessionId).buildSubject();

    if(!subject.isAuthenticated()) {
      subject.login(token);
      ThreadContext.bind(subject);
      ensureProfile(subject);
    }
    return subject.isAuthenticated() ? subject : null;
  }

  /**
   * Trigger some processing after the login evaluation.
   *
   * @param subject
   */
  protected abstract void ensureProfile(Subject subject);

}
