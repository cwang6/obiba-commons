/*
 * Copyright (c) 2013 OBiBa. All rights reserved.
 *
 * This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.obiba.git;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import javax.annotation.Nullable;

public class CommitInfo {

  private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");

  static {
    DATE_FORMAT.setTimeZone(TimeZone.getTimeZone("UTC"));
  }

  private String author;

  private Date date;

  private String comment;

  private String commitId;

  private List<String> diffEntries;

  private String blob;

  private boolean isHead = false;

  private boolean isCurrent = false;

  public String getAuthor() {
    return author;
  }

  public Date getDate() {
    return (Date) date.clone();
  }

  public String getDateAsIso8601() {
    return DATE_FORMAT.format(date);
  }

  public String getComment() {
    return comment;
  }

  public String getCommitId() {
    return commitId;
  }

  @Nullable
  public List<String> getDiffEntries() {
    return diffEntries == null ? null : diffEntries.subList(0, diffEntries.size());
  }

  public String getBlob() {
    return blob;
  }

  public boolean getIsHead() {
    return isHead;
  }

  public boolean getIsCurrent() {
    return isCurrent;
  }

  public String toString() {
    return String.format("CommitInfo Id: %s, Author: %s, Date: %s\n%s", commitId, author, date, comment);
  }

  @SuppressWarnings("ParameterHidesMemberVariable")
  public static class Builder {
    private String author;

    private Date date;

    private String comment;

    private String commitId;

    private List<String> diffEntries;

    private String blob;

    private boolean isHead = false;

    private boolean isCurrent = false;

    public static Builder createFromObject(CommitInfo commitInfo) {
      return new Builder().setAuthor(commitInfo.author).setComment(commitInfo.comment).setCommitId(commitInfo.commitId)
          .setDate(commitInfo.date).setDiffEntries(commitInfo.diffEntries);
    }

    public Builder setAuthor(String value) {
      author = value;
      return this;
    }

    @SuppressWarnings("AssignmentToDateFieldFromParameter")
    public Builder setDate(Date date) {
      this.date = date;
      return this;
    }

    public Builder setComment(String comment) {
      this.comment = comment;
      return this;
    }

    public Builder setCommitId(String value) {
      commitId = value;
      return this;
    }

    public Builder setDiffEntries(List<String> value) {
      diffEntries = value;
      return this;
    }

    public Builder setBlob(String value) {
      blob = value;
      return this;
    }

    public Builder setIsHead(boolean value) {
      isHead = value;
      return this;
    }

    public Builder setIsCurrent(boolean value) {
      isCurrent = value;
      return this;
    }

    public CommitInfo build() {
      CommitInfo commitInfo = new CommitInfo();
      commitInfo.author = author;
      commitInfo.date = date;
      commitInfo.comment = comment;
      commitInfo.commitId = commitId;
      commitInfo.diffEntries = diffEntries;
      commitInfo.blob = blob;
      commitInfo.isHead = isHead;
      commitInfo.isCurrent = isCurrent;
      return commitInfo;
    }
  }
}
