package app.models.Announcement;

import app.models.GenericDAO;

public class AnnouncementDAO extends GenericDAO<Announcement> {
  public AnnouncementDAO(){
    super(Announcement.class);
  }
}
