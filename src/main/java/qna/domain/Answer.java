package qna.domain;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import qna.CannotDeleteException;
import qna.NotFoundException;
import qna.UnAuthorizedException;

@Entity
public class Answer extends AbstractEntity {

  @ManyToOne(optional = false)
  @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_writer"))
  private User writer;

  @ManyToOne(optional = false)
  @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_to_question"))
  private Question question;

  @Lob
  private String contents;

  private boolean deleted = false;

  public Answer() {
  }

  public Answer(User writer, Question question, String contents) {
    this(null, writer, question, contents);
  }

  public Answer(Long id, User writer, Question question, String contents) {
    super(id);

    if (writer == null) {
      throw new UnAuthorizedException();
    }

    if (question == null) {
      throw new NotFoundException();
    }

    this.writer = writer;
    this.question = question;
    this.contents = contents;
  }

  public boolean isDeleted() {
    return deleted;
  }

  private boolean isOwner(User writer) {
    return this.writer.equals(writer);
  }

  public User getWriter() {
    return writer;
  }

  public void toQuestion(Question question) {
    this.question = question;
  }

  @Override
  public String toString() {
    return "Answer [id=" + getId() + ", writer=" + writer + ", contents=" + contents + "]";
  }

  public void delete(User questionOwner) throws CannotDeleteException {
    checkOwner(questionOwner);
    this.deleted = true;
  }

  public void checkOwner(User questionOwner) throws CannotDeleteException {
    if (!isOwner(questionOwner)) {
      throw new CannotDeleteException("다른 사람이 쓴 답변이 있어 삭제할 수 없습니다.");
    }
  }
}
