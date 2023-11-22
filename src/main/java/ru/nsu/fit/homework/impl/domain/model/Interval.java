package ru.nsu.fit.homework.impl.domain.model;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.UserType;
import org.postgresql.util.PGInterval;

import java.io.Serializable;
import java.sql.*;
import java.time.Duration;

public class Interval implements UserType<Duration> {

    @Override
    public int getSqlType() {
        return Types.OTHER;
    }

    @Override
    public Class<Duration> returnedClass() {
        return Duration.class;
    }

    @Override
    public boolean equals(Duration x, Duration y) {
        if (x == null) {
            return y == null;
        }

        return x.equals(y);
    }

    @Override
    public int hashCode(Duration x) {
        return x.hashCode();
    }

    @Override
    public Duration nullSafeGet(ResultSet rs, int position, SharedSessionContractImplementor session, Object owner) throws SQLException {
        String interval = rs.getString(position);
        if (rs.wasNull() || interval == null) {
            return null;
        }

        PGInterval pgInterval = new PGInterval(interval);
        Date epoch = new Date(0L);
        pgInterval.add(epoch);

        return Duration.ofSeconds(epoch.getTime() / 1000);
    }

    @Override
    public void nullSafeSet(PreparedStatement st, Duration value, int index, SharedSessionContractImplementor session) throws SQLException {
        if (value == null) {
            st.setNull(index, Types.OTHER);
        } else {
            st.setObject(index, getInterval(value.toSeconds()).getValue(), Types.OTHER);
        }
    }

    public static PGInterval getInterval(Long seconds) {
        return new PGInterval(0, 0, 0, 0, 0, seconds);
    }

    @Override
    public Duration deepCopy(Duration value) {
        return value;
    }

    @Override
    public boolean isMutable() {
        return true;
    }

    @Override
    public Serializable disassemble(Duration value) {
        return value;
    }

    @Override
    public Duration assemble(Serializable cached, Object owner) {
        return (Duration) cached;
    }

    @Override
    public Duration replace(Duration detached, Duration managed, Object owner) {
        return detached;
    }
}